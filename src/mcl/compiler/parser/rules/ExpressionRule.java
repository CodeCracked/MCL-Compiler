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
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        if (parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.INT) || parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.FLOAT))
        {
            Token type = parser.getCurrentToken();

            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected identifier"));

            Token identifier = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();

            if (parser.getCurrentToken().type() != TokenType.ASSIGN) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected '='"));

            result.registerAdvancement();
            parser.advance();
            AbstractNode valueExpression = result.register(GrammarRules.EXPRESSION.build(parser));
            if (result.error() != null) return result;
            else return result.success(new VarAssignNode(type, identifier, valueExpression));
        }

        AbstractNode node = result.register(GrammarRules.binaryOperationRule(parser, GrammarRules.FACTOR, Set.of(TokenType.PLUS, TokenType.MINUS)));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected 'int', 'float', int, float, identifier, '+', '-', or '('"));

        return result.success(node);
    }
}
