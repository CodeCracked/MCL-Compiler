package compiler.core.parser.grammar.expressions;

import compiler.core.lexer.Token;
import compiler.core.parser.IParserRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;

import java.util.function.Predicate;

public interface IExpressionGrammarRule extends IParserRule
{
    boolean canBuild(Parser parser, ExpressionRule expressionRule);
    Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule);
    
    default boolean expectTokenTypes(Parser parser, Enum<?>... types)
    {
        Predicate<Token>[] predicates = new Predicate[types.length];
        for (int i = 0; i < types.length; i++)
        {
            Enum<?> type = types[i];
            predicates[i] = token -> token.type() == type;
        }
        return expectTokens(parser, predicates);
    }
    default boolean expectTokens(Parser parser, Predicate<Token>... tokenPredicates)
    {
        parser.markPosition();
        for (Predicate<Token> predicate : tokenPredicates)
        {
            if (!predicate.test(parser.getCurrentToken()))
            {
                parser.revertPosition();
                return false;
            }
            else parser.advance();
        }
        
        parser.revertPosition();
        return true;
    }
}
