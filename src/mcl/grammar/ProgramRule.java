package mcl.grammar;

import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.util.Result;
import mcl.nodes.NamespaceNode;
import mcl.nodes.ProgramNode;

import java.util.ArrayList;
import java.util.List;

public class ProgramRule implements IGrammarRule<ProgramNode>
{
    @Override
    public Result<ProgramNode> build(Parser parser)
    {
        Result<ProgramNode> result = new Result<>();
        List<NamespaceNode> namespaces = new ArrayList<>();
        
        while (parser.getCurrentToken() != null)
        {
            namespaces.add(result.register(MCLRules.NAMESPACE.build(parser)));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(new ProgramNode(namespaces));
    }
}
