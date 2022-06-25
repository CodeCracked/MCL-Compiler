package mcl.compiler.parser.rules.events;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.events.EventDefinitionNode;

public class EventDefinitionRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Keyword
        Token keyword = parser.getCurrentToken();
        if (!keyword.isKeyword(MCLKeywords.EVENT)) return result.failure(new MCLSyntaxError(parser, "Expected 'event'"));
        result.registerAdvancement();
        parser.advance();

        // Identifier
        Token identifier = parser.getCurrentToken();
        if (identifier.type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser, "Expected identifier"));
        result.registerAdvancement();
        parser.advance();

        // Parameter List
        ParameterListNode parameterList = (ParameterListNode) result.register(GrammarRules.PARAMETER_LIST.build(parser));
        if (result.error() != null) return result;
        return result.success(new EventDefinitionNode(keyword, identifier, parameterList));
    }
}
