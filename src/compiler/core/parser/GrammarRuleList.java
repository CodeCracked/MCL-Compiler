package compiler.core.parser;

import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarRuleList<T extends AbstractNode> implements IGrammarRule<T>
{
    protected final String error;
    protected final List<IGrammarRule<? extends T>> rules;
    
    @SafeVarargs
    public GrammarRuleList(String error, IGrammarRule<? extends T>... rules)
    {
        this.error = error;
        this.rules = new ArrayList<>();
        Collections.addAll(this.rules, rules);
    }
    
    public void addRule(IGrammarRule<? extends T> rule) { this.rules.add(rule); }
    
    @Override
    public Result<T> build(Parser parser)
    {
        for (IGrammarRule<? extends T> rule : rules)
        {
            parser.markPosition();
            Result<?> result = rule.build(parser);
            
            if (result.getFailure() != null) parser.revertPosition();
            else
            {
                parser.unmarkPosition();
                return Result.of((T)result.get());
            }
        }
        return Result.fail(new CompilerException(parser.getCurrentToken().start(), error));
    }
}
