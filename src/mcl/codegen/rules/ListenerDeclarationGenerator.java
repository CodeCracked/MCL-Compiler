package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;

import java.io.IOException;

public class ListenerDeclarationGenerator implements ICodeGenRule<ListenerDeclarationNode>
{
    @Override
    public Result<Void> generate(ListenerDeclarationNode node, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Generate Body
        result.register(context.generate(node.body));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
