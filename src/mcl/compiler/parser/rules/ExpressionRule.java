package mcl.compiler.parser.rules;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.VarAssignNode;

import java.util.Set;

public final class ExpressionRule implements GrammarRule
{
    private final Set<Token> comparisonOperators = Set.of(Token.description(TokenType.KEYWORD, MCLKeywords.AND), Token.description(TokenType.KEYWORD, MCLKeywords.OR));

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        if (parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.INT) || parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.FLOAT))
        {
            Token type = parser.getCurrentToken();

            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));

            Token identifier = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.ASSIGN) return result.failure(new MCLSyntaxError(parser, "Expected '='"));

            result.registerAdvancement();
            parser.advance();
            AbstractNode valueExpression = result.register(GrammarRules.EXPRESSION.build(parser));
            if (result.error() != null) return result;
            else return result.success(new VarAssignNode(type, identifier, valueExpression));
        }

        AbstractNode node = result.register(GrammarRules.binaryOperationRule(parser, GrammarRules.COMPARISON_EXPRESSION, comparisonOperators));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser, "Expected 'int', 'float', 'not', int, float, identifier, '+', '-', or '('"));

        return result.success(node);
    }
}
