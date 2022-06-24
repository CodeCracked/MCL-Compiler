package mcl.compiler.parser.rules.variables;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.GrammarRule;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;

public class VariableSignatureRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        Token type = parser.getCurrentToken();
        if (!type.isKeyword(MCLKeywords.VARIABLE_TYPES)) return result.failure(new MCLSyntaxError(parser, "Expected variable type"));
        result.registerAdvancement();
        parser.advance();

        Token identifier = parser.getCurrentToken();
        if (identifier.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        return result.success(new VariableSignatureNode(type, identifier));
    }
}
