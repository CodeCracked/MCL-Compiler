package mcl.grammar.declarations;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.DefaultRules;
import compiler.core.parser.nodes.IdentifierNode;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.nodes.declarations.EventDeclarationNode;

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
        
        // Check if there is a parameter list
        if (parser.getCurrentToken().type() == GrammarTokenType.LPAREN)
        {
            // TODO: Create actual parameter list rule
            // Opening Parenthesis
            Token openingParenthesis = parser.getCurrentToken();
            parser.advance();
            result.registerAdvancement();
            
            // Closing Parenthesis
            Token closingParenthesis = parser.getCurrentToken();
            if (closingParenthesis.type() != GrammarTokenType.RPAREN) return result.failure(UnexpectedTokenException.expected(parser, "'}'"));
            parser.advance();
            result.registerAdvancement();
        }
        
        // Semicolon
        Token semicolon = parser.getCurrentToken();
        if (semicolon.type() != GrammarTokenType.SEMICOLON) return result.failure(UnexpectedTokenException.expected(parser, "';'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new EventDeclarationNode(keyword, identifier, semicolon));
    }
}
