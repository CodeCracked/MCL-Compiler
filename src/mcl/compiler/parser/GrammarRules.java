package mcl.compiler.parser;

import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.nodes.BinaryOpNode;
import mcl.compiler.parser.rules.AtomRule;
import mcl.compiler.parser.rules.ExpressionRule;
import mcl.compiler.parser.rules.FactorRule;

import java.util.Set;

public class GrammarRules
{
    public static final GrammarRule EXPRESSION = new ExpressionRule();
    public static final GrammarRule FACTOR = new FactorRule();
    public static final GrammarRule ATOM = new AtomRule();

    public static ParseResult binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<TokenType> operations)
    {
        ParseResult result = new ParseResult();
        AbstractNode left = result.register(argumentRule.build(parser));
        if (result.error() != null) return result;

        while (parser.getCurrentToken() != null && operations.contains(parser.getCurrentToken().type()))
        {
            Token operation = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();
            AbstractNode right = result.register(argumentRule.build(parser));
            if (result.error() != null) return result;

            left = new BinaryOpNode(left, operation, right);
        }

        return result.success(left);
    }
}
