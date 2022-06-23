package mcl.compiler.parser.rules;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.VarAssignNode;
import mcl.compiler.parser.nodes.VarDeclarationNode;

import java.util.Set;

public class StatementRule implements GrammarRule
{
    private final Set<Token> assignOperations = Token.descriptions(TokenType.ASSIGN, TokenType.ASSIGN_PLUS, TokenType.ASSIGN_MINUS, TokenType.ASSIGN_MUL, TokenType.ASSIGN_DIV, TokenType.ASSIGN_MOD);

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        if (parser.getCurrentToken().isKeyword(MCLKeywords.TYPES)) return variableDeclaration(parser, result);
        else if (parser.getCurrentToken().type() == TokenType.IDENTIFIER) return variableAssignDeclaration(parser, result);

        return result.failure(new MCLSyntaxError(parser, "Not a statement"));
    }

    private ParseResult variableDeclaration(MCLParser parser, ParseResult result)
    {
        Token keyword = parser.getCurrentToken();
        result.registerAdvancement();
        parser.advance();

        Token identifier = parser.getCurrentToken();
        if (identifier.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        if (parser.getCurrentToken().type() != TokenType.ASSIGN) return result.failure(new MCLSyntaxError(parser, "Expected assignment operator"));
        result.registerAdvancement();
        parser.advance();

        AbstractNode value = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        return result.success(new VarDeclarationNode(keyword, identifier, value));
    }
    private ParseResult variableAssignDeclaration(MCLParser parser, ParseResult result)
    {
        Token identifier = parser.getCurrentToken();
        result.registerAdvancement();
        parser.advance();

        Token operation = parser.getCurrentToken();
        if (!operation.matches(assignOperations)) return result.failure(new MCLSyntaxError(parser, "Expected '='"));
        result.registerAdvancement();
        parser.advance();

        AbstractNode value = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        return result.success(new VarAssignNode(identifier, operation, value));
    }
}
