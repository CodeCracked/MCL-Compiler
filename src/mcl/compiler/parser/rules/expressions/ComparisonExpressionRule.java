package mcl.compiler.parser.rules.expressions;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.expressions.UnaryOpNode;

import java.util.Set;

public class ComparisonExpressionRule implements GrammarRule
{
    private final Set<Token> comparisonOperators = Token.descriptions(TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.LESS, TokenType.GREATER, TokenType.LESS_OR_EQUAL, TokenType.GREATER_OR_EQUAL);

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        if (parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.NOT))
        {
            Token operation = parser.getCurrentToken();

            result.registerAdvancement();
            parser.advance();

            AbstractNode node = result.register(GrammarRules.COMPARISON_EXPRESSION.build(parser));
            if (result.error() != null) return result;
            else return result.success(new UnaryOpNode(operation, node));
        }
        
        AbstractNode node = result.register(GrammarRules.binaryOperationRule(parser, GrammarRules.ARITHMETIC_EXPRESSION, comparisonOperators));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser, "Expected variable type, identifier, operator, or '('"));
        else return result.success(node);
    }
}
