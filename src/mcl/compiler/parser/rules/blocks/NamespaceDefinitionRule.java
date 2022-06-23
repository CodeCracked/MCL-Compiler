package mcl.compiler.parser.rules.blocks;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.NamespaceDefinitionNode;

public class NamespaceDefinitionRule implements GrammarRule
{
    public static class NamespaceBodyRule extends BlockStatementRule
    {
        public NamespaceBodyRule()
        {
            super(1);
        }

        @Override
        protected ParseResult buildStatement(MCLParser parser)
        {
            ParseResult result = new ParseResult();

            if (parser.getCurrentToken().isKeyword(MCLKeywords.FUNC))
            {
                AbstractNode function = result.register(GrammarRules.FUNCTION.build(parser));
                if (result.error() != null) return result;
                return result.success(function);
            }

            else if (parser.getCurrentToken().isKeyword(MCLKeywords.VARIABLE_TYPES))
            {
                AbstractNode variableDefinition = result.register(GrammarRules.VARIABLE_DEFINITION.build(parser));
                if (result.error() != null) return result;
                return result.success(variableDefinition);
            }

            return result.failure(new MCLSyntaxError(parser, "Expected 'func' or variable type"));
        }
    }

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

        AbstractNode body = result.register(new NamespaceBodyRule().build(parser));
        if (result.error() != null) return result;

        return result.success(new NamespaceDefinitionNode(keyword, identifier, body));
    }
}
