package mcl.compiler.parser.rules.blocks;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.GrammarRule;
import mcl.compiler.parser.GrammarRules;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;
import mcl.compiler.parser.nodes.blocks.FunctionDefinitionNode;

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

        // Parameter List
        ParameterListNode parameters = (ParameterListNode)result.register(GrammarRules.PARAMETER_LIST.build(parser));
        if (result.error() != null) return result;

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
        BlockStatementNode body = (BlockStatementNode)result.register(GrammarRules.blockStatement(parser.getCurrentIndent() + 1).build(parser));
        if (result.error() != null) return result;

        return result.success(new FunctionDefinitionNode(keyword, identifier, parameters, returnType, body));
    }
}
