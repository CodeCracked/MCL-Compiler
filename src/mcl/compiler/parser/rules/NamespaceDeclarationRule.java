package mcl.compiler.parser.rules;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.NamespaceDeclarationNode;

public class NamespaceDeclarationRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        if (!parser.getCurrentToken().matches(TokenType.KEYWORD, "namespace")) return result.failure(new MCLSyntaxError(parser, "Expected 'namespace'"));

        Token keyword = parser.getCurrentToken();
        result.registerAdvancement();
        parser.advance();
        if (parser.getCurrentToken().type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));

        Token identifier = parser.getCurrentToken();
        result.registerAdvancement();
        parser.advance();

        AbstractNode body = result.register(GrammarRules.blockStatement(1).build(parser));
        if (result.error() != null) return result;

        return result.success(new NamespaceDeclarationNode(keyword, identifier, body));
    }
}
