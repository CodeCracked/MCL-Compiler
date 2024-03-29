package mcl.parser.grammar;

import compiler.core.lexer.Token;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.NamespaceNode;

public class NamespaceRule implements IGrammarRule<NamespaceNode>
{
    @Override
    public Result<NamespaceNode> build(Parser parser)
    {
        Result<NamespaceNode> result = new Result<>();
    
        // Keyword
        Token keyword = parser.getCurrentToken();
        if (!keyword.type().equals(MCLKeyword.NAMESPACE)) return result.failure(UnexpectedTokenException.expected(parser, "'namespace'"));
        parser.advance();
        result.registerAdvancement();
        
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Body
        BlockNode body = result.register(MCLRules.NAMESPACE_BODY.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new NamespaceNode(keyword, identifier, body));
    }
}
