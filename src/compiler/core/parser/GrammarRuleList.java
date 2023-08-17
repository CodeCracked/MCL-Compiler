package compiler.core.parser;

import compiler.core.exceptions.CompilerException;
import compiler.core.util.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarRuleList implements IGrammarRule<AbstractNode>
{
    protected final String error;
    protected final List<IGrammarRule<?>> rules;
    
    public GrammarRuleList(String error, IGrammarRule<?>... rules)
    {
        this.error = error;
        this.rules = new ArrayList<>();
        Collections.addAll(this.rules, rules);
    }
    
    @Override
    public Result<AbstractNode> build(Parser parser)
    {
        for (IGrammarRule<?> rule : rules)
        {
            parser.markPosition();
            Result<?> result = rule.build(parser);
            
            if (result.getFailure() != null) parser.revertPosition();
            else
            {
                parser.unmarkPosition();
                return Result.of((AbstractNode) result.get());
            }
        }
        return Result.fail(new CompilerException(parser.getCurrentToken().start(), error));
    }
}
