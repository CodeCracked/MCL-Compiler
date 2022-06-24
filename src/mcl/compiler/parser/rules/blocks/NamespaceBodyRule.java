package mcl.compiler.parser.rules.blocks;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.NamespaceBodyNode;

import java.util.ArrayList;
import java.util.List;

public class NamespaceBodyRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        List<AbstractNode> definitions = new ArrayList<>();
        int start = parser.getCurrentToken().startPosition();

        while (parser.getCurrentToken().type() == TokenType.NEWLINE)
        {
            // Clear Newline
            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.INDENT) break;

            // Check Indent
            int indent = (int)parser.getCurrentToken().value();
            if (indent < 1) break;
            else if (indent > 1) return result.failure(new MCLSyntaxError(parser, "Expected indent of size 1"));
            result.registerAdvancement();
            parser.advance();

            // Function Definition
            if (parser.getCurrentToken().isKeyword(MCLKeywords.FUNC))
            {
                AbstractNode function = result.register(GrammarRules.FUNCTION.build(parser));
                if (result.error() != null) return result;
                definitions.add(function);
            }

            // Variable Definition
            else if (parser.getCurrentToken().isKeyword(MCLKeywords.VARIABLE_TYPES))
            {
                AbstractNode variableDefinition = result.register(GrammarRules.VARIABLE_DEFINITION.build(parser));
                if (result.error() != null) return result;
                definitions.add(variableDefinition);
            }
        }

        return result.success(new NamespaceBodyNode(definitions, start));
    }
}
