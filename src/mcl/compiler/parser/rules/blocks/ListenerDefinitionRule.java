package mcl.compiler.parser.rules.blocks;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.LocationNode;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.blocks.ListenerDefinitionNode;

public class ListenerDefinitionRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Keyword
        Token keyword = parser.getCurrentToken();
        if (!keyword.isKeyword(MCLKeywords.LISTENER)) return result.failure(new MCLSyntaxError(parser, "Expected 'listener'"));
        result.registerAdvancement();
        parser.advance();

        // Event Location
        LocationNode location = (LocationNode) result.register(GrammarRules.LOCATION.build(parser));
        if (result.error() != null) return result;

        // Parameter List
        ParameterListNode parameterList = (ParameterListNode) result.register(GrammarRules.PARAMETER_LIST.build(parser));
        if (result.error() != null) return result;

        // Body
        AbstractNode body = result.register(GrammarRules.blockStatement(parser.getCurrentIndent() + 1).build(parser));
        if (result.error() != null) return result;
        return result.success(new ListenerDefinitionNode(keyword, location, parameterList, body));
    }
}
