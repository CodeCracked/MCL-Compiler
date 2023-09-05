package mcl.parser.grammar.declarations;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;
import mcl.lexer.MCLKeyword;
import mcl.parser.nodes.declarations.EventDeclarationNode;

public class EventDeclarationRule implements IGrammarRule<EventDeclarationNode>
{
    @Override
    public Result<EventDeclarationNode> build(Parser parser)
    {
        Result<EventDeclarationNode> result = new Result<>();
        
        // Keyword
        Token keyword = parser.getCurrentToken();
        if (keyword.type() != MCLKeyword.EVENT) return result.failure(UnexpectedTokenException.expected(parser, "'event'"));
        parser.advance();
        result.registerAdvancement();
        
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Optional Parameter List
        ParameterListNode parameterList = (parser.getCurrentToken().type() == GrammarTokenType.LPAREN) ? result.register(DefaultRules.PARAMETER_LIST.build(parser)) : ParameterListNode.empty(parser);
        if (result.getFailure() != null) return result;
        
        // Semicolon
        Token semicolon = parser.getCurrentToken();
        if (semicolon.type() != GrammarTokenType.SEMICOLON) return result.failure(UnexpectedTokenException.expected(parser, "';'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new EventDeclarationNode(keyword, identifier, parameterList, semicolon));
    }
}
