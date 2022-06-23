package mcl.compiler.parser;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.nodes.BinaryOpNode;
import mcl.compiler.parser.rules.*;

import java.util.Set;

public class GrammarRules
{
    public static final GrammarRule EXPRESSION = new ExpressionRule();
    public static final GrammarRule COMPARISON_EXPRESSION = new ComparisonExpressionRule();
    public static final GrammarRule ARITHMETIC_EXPRESSION = new ArithmeticExpressionRule();
    public static final GrammarRule FACTOR = new FactorRule();
    public static final GrammarRule ATOM = new AtomRule();

    public static ParseResult binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<Token> operations)
    {
        ParseResult result = new ParseResult();
        AbstractNode left = result.register(argumentRule.build(parser));
        if (result.error() != null) return result;

        while (parser.getCurrentToken() != null)
        {
            boolean foundOperation = false;
            for (Token operationCheck : operations)
            {
                if (parser.getCurrentToken().matches(operationCheck))
                {
                    Token operation = parser.getCurrentToken();
                    result.registerAdvancement();
                    parser.advance();
                    AbstractNode right = result.register(argumentRule.build(parser));
                    if (result.error() != null) return result;

                    left = new BinaryOpNode(left, operation, right);
                    foundOperation = true;
                    break;
                }
            }
            if (!foundOperation) break;
        }

        return result.success(left);
    }
}
