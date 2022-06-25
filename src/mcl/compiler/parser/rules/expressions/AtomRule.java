package mcl.compiler.parser.rules.expressions;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.expressions.NumberNode;
import mcl.compiler.parser.nodes.expressions.UnaryOpNode;
import mcl.compiler.parser.nodes.expressions.VariableAccessNode;

public class AtomRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        Token token = parser.getCurrentToken();

        // Unary Operations
        if (token.type() == TokenType.PLUS || token.type() == TokenType.MINUS)
        {
            result.registerAdvancement();
            parser.advance();
            AbstractNode factor = result.register(GrammarRules.ATOM.build(parser));
            if (result.error() != null) return result;
            else return result.success(new UnaryOpNode(token, factor));
        }

        // Function Call or Variable Access
        else if (token.type() == TokenType.IDENTIFIER)
        {
            // Function Call
            if (parser.peekNextToken().type() == TokenType.LPAREN)
            {
                AbstractNode functionCall = result.register(GrammarRules.FUNCTION_CALL.build(parser));
                if (result.error() != null) return result;
                return result.success(functionCall);
            }

            // Variable Access
            else
            {
                result.registerAdvancement();
                parser.advance();
                return result.success(new VariableAccessNode(token));
            }
        }

        // Number Literals
        else if (token.type() == TokenType.INT || token.type() == TokenType.FLOAT)
        {
            result.registerAdvancement();
            parser.advance();
            return result.success(new NumberNode(token));
        }

        // Parenthesis Expressions
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
