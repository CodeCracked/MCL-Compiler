package mcl.compiler.parser.rules.variables;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;

import java.util.ArrayList;
import java.util.List;

public class ParameterListRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Check for Parameter List Start
        Token openToken = parser.getCurrentToken();
        if (openToken.type() != TokenType.LPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
        result.registerAdvancement();
        parser.advance();

        // Parameter List
        List<VariableSignatureNode> parameters = new ArrayList<>();
        if (parser.getCurrentToken().isKeyword(MCLKeywords.VARIABLE_TYPES))
        {
            do
            {
                if (parser.getCurrentToken().type() == TokenType.COMMA)
                {
                    result.registerAdvancement();
                    parser.advance();
                }

                AbstractNode parameter = result.register(GrammarRules.VARIABLE_SIGNATURE.build(parser));
                if (result.error() != null) return result;
                parameters.add((VariableSignatureNode) parameter);
            }
            while (parser.getCurrentToken().type() == TokenType.COMMA);
        }

        // Check for Parameter List End
        Token closeToken = parser.getCurrentToken();
        if (closeToken.type() != TokenType.RPAREN) return result.failure(new MCLSyntaxError(parser, "Expected ')'"));
        result.registerAdvancement();
        parser.advance();

        return result.success(new ParameterListNode(openToken, parameters, closeToken));
    }
}
