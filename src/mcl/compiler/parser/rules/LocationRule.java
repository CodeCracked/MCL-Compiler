package mcl.compiler.parser.rules;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.GrammarRule;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.parser.nodes.LocationNode;

public class LocationRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        Token first = parser.getCurrentToken();
        if (first.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        Token second = null;
        if (parser.getCurrentToken().type() == TokenType.COLON)
        {
            result.registerAdvancement();
            parser.advance();

            second = parser.getCurrentToken();
            if (second.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
            result.registerAdvancement();
            parser.advance();
        }

        return result.success(new LocationNode(first, second));
    }
}
