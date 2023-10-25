package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;

import java.io.IOException;

public class ListenerDeclarationGenerator implements ICodeGenRule<ListenerDeclarationNode>
{
    @Override
    public Result<Void> generate(ListenerDeclarationNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Open Listener File
        context.openSubdirectory("functions", "listeners");
        context.openFile(component.functionName() + ".mcfunction", file ->
        {
            file.println("# " + component.getMCLDescription());
            file.println();
        });
        
        // Generate Body
        result.register(context.generate(component.body));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
