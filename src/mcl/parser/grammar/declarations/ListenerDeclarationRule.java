package mcl.parser.grammar.declarations;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;

public class ListenerDeclarationRule implements IGrammarRule<ListenerDeclarationNode>
{
    @Override
    public Result<ListenerDeclarationNode> build(Parser parser)
    {
        Result<ListenerDeclarationNode> result = new Result<>();
        
        // Keyword
        Token keyword = parser.getCurrentToken();
        if (keyword.type() != MCLKeyword.LISTENER) return result.failure(UnexpectedTokenException.expected(parser, "'listener'"));
        parser.advance();
        result.registerAdvancement();
        
        // Event Identifier
        QualifiedIdentifierNode event = result.register(MCLRules.QUALIFIED_IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Parameter List
        ParameterListNode parameters = result.register(DefaultRules.PARAMETER_LIST.build(parser));
        if (result.getFailure() != null) return result;
        
        // Body
        BlockNode body = result.register(MCLRules.FUNCTION_BODY.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new ListenerDeclarationNode(keyword, event, parameters, body));
    }
}
