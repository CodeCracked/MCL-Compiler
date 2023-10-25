package compiler.core.parser;

import compiler.core.lexer.Token;
import compiler.core.util.Pair;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GrammarRuleChooser<T extends AbstractNode> implements IGrammarRule<T>
{
    private final List<Pair<Predicate<Parser>, IGrammarRule<? extends T>>> rules;
    private final String fallbackError;
    
    public GrammarRuleChooser(String fallbackError)
    {
        this.rules = new ArrayList<>();
        this.fallbackError = fallbackError;
    }
    
    public GrammarRuleChooser<T> deepCopy()
    {
        GrammarRuleChooser<T> copy = new GrammarRuleChooser<>(fallbackError);
        for (Pair<Predicate<Parser>, IGrammarRule<? extends T>> rule : rules) copy.rules.add(new Pair<>(rule.first(), rule.second()));
        return copy;
    }
    
    public GrammarRuleChooser<T> addRule(IGrammarRule<? extends T> rule) { return addRule(rule, parser -> true); }
    public GrammarRuleChooser<T> addRule(IGrammarRule<? extends T> rule, Class<?>... tokenLists)
    {
        return addRule(rule, parser ->
        {
            for (Class<?> tokenList : tokenLists)
            {
                if (!parser.getCurrentToken().type().getClass().equals(tokenList)) return false;
                else parser.advance();
            }
            return true;
        });
    }
    public GrammarRuleChooser<T> addRule(IGrammarRule<? extends T> rule, Enum<?>... tokenTypes)
    {
        return addRule(rule, parser ->
        {
            for (Enum<?> tokenType : tokenTypes)
            {
                if (parser.getCurrentToken().type() != tokenType) return false;
                else parser.advance();
            }
            return true;
        });
    }
    public GrammarRuleChooser<T> addRule(IGrammarRule<? extends T> rule, Predicate<Token>... tokenPredicates)
    {
        return addRule(rule, parser ->
        {
            for (Predicate<Token> tokenPredicate : tokenPredicates)
            {
                if (!tokenPredicate.test(parser.getCurrentToken())) return false;
                else parser.advance();
            }
            return true;
        });
    }
    public GrammarRuleChooser<T> addRule(IGrammarRule<? extends T> rule, Predicate<Parser> predicate)
    {
        this.rules.add(new Pair<>(predicate, rule));
        return this;
    }
    
    
    @Override
    public Result<T> build(Parser parser)
    {
        Result<T> result = new Result<>();
        
        for (Pair<Predicate<Parser>, IGrammarRule<? extends T>> choice : rules)
        {
            parser.markPosition();
            boolean choose = choice.first().test(parser);
            parser.revertPosition();
            
            if (choose)
            {
                T node = result.register(choice.second().build(parser));
                return result.success(node);
            }
        }
        
        return result.failure(UnexpectedTokenException.explicit(parser, fallbackError));
    }
}
