package mcl.compiler.parser.rules.expressions;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.expressions.FunctionCallNode;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Function Identifier
        Token identifier = parser.getCurrentToken();
        if (identifier.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        // Check Argument List Start
        if (parser.getCurrentToken().type() != TokenType.LPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
        result.registerAdvancement();
        parser.advance();

        // Get Arguments
        List<AbstractNode> arguments = new ArrayList<>();
        if (parser.getCurrentToken().type() != TokenType.RPAREN)
        {
            do
            {
                if (parser.getCurrentToken().type() == TokenType.COMMA)
                {
                    result.registerAdvancement();
                    parser.advance();
                }

                AbstractNode expression = result.register(GrammarRules.EXPRESSION.build(parser));
                if (result.error() != null) return result;
                arguments.add(expression);
            }
            while (parser.getCurrentToken().type() == TokenType.COMMA);
        }

        // Check Argument List End
        Token closingToken = parser.getCurrentToken();
        if (closingToken.type() != TokenType.RPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
        result.registerAdvancement();
        parser.advance();

        return result.success(new FunctionCallNode(identifier, arguments, closingToken));
    }
}
