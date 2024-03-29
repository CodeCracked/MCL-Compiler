package mcl.parser.grammar;

import compiler.core.lexer.types.TokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;

import java.util.ArrayList;
import java.util.List;

public class MCLFileRule implements IGrammarRule<MCLSourceNode>
{
    @Override
    public Result<MCLSourceNode> build(Parser parser)
    {
        Result<MCLSourceNode> result = new Result<>();
        List<NamespaceNode> namespaces = new ArrayList<>();
        
        while (parser.getCurrentToken().type() != TokenType.END_OF_FILE)
        {
            namespaces.add(result.register(MCLRules.NAMESPACE.build(parser)));
            if (result.getFailure() != null) return result;
        }
        
        // Clear end-of-file
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new MCLSourceNode(namespaces));
    }
}
