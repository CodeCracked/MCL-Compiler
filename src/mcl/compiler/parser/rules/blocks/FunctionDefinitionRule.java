package mcl.compiler.parser.rules.blocks;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.FunctionDefinitionNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        Token keyword = parser.getCurrentToken();
        if (!keyword.isKeyword(MCLKeywords.FUNC)) return result.failure(new MCLSyntaxError(parser, "Expected 'func'"));
        result.registerAdvancement();
        parser.advance();

        // Function Name
        Token identifier = parser.getCurrentToken();
        if (identifier.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        // Check for Parameter List Start
        if (parser.getCurrentToken().type() != TokenType.LPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
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

        // Check for Parameter List Start
        if (parser.getCurrentToken().type() != TokenType.RPAREN) return result.failure(new MCLSyntaxError(parser, "Expected ')'"));
        result.registerAdvancement();
        parser.advance();

        // Return Type
        Token returnType = null;
        if (parser.getCurrentToken().isKeyword(MCLKeywords.RETURN))
        {
            result.registerAdvancement();
            parser.advance();

            if (!parser.getCurrentToken().isKeyword(MCLKeywords.VARIABLE_TYPES)) return result.failure(new MCLSyntaxError(parser, "Expected variable type"));
            returnType = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();
        }

        // Body
        AbstractNode body = result.register(GrammarRules.blockStatement(parser.getCurrentIndent() + 1).build(parser));
        if (result.error() != null) return result;

        return result.success(new FunctionDefinitionNode(keyword, identifier, parameters, returnType, body));
    }
}
