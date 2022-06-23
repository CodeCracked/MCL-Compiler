package mcl.compiler.parser.rules;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.NumberNode;
import mcl.compiler.parser.nodes.UnaryOpNode;
import mcl.compiler.parser.nodes.VarAccessNode;

public class AtomRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        Token token = parser.getCurrentToken();

        if (token.type() == TokenType.PLUS || token.type() == TokenType.MINUS)
        {
            result.registerAdvancement();
            parser.advance();
            AbstractNode factor = result.register(GrammarRules.ATOM.build(parser));
            if (result.error() != null) return result;
            else return result.success(new UnaryOpNode(token, factor));
        }

        else if (token.type() == TokenType.IDENTIFIER)
        {
            result.registerAdvancement();
            parser.advance();
            return result.success(new VarAccessNode(token));
        }

        else if (token.type() == TokenType.INT || token.type() == TokenType.FLOAT)
        {
            result.registerAdvancement();
            parser.advance();
            return result.success(new NumberNode(token));
        }

        else if (token.type() == TokenType.LPAREN)
        {
            result.registerAdvancement();
            parser.advance();
            AbstractNode expression = result.register(GrammarRules.EXPRESSION.build(parser));
            if (result.error() != null) return result;
            if (parser.getCurrentToken().type() == TokenType.RPAREN)
            {
                result.registerAdvancement();
                parser.advance();
                return result.success(expression);
            }
            else return result.failure(new MCLSyntaxError(parser, "Expected ')'"));
        }

        return result.failure(new MCLSyntaxError(parser, "Expected number, identifier, '+', '-', or '('"));
    }
}
