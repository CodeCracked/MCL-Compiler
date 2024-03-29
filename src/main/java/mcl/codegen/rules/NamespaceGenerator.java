package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.NamespaceNode;

import java.io.IOException;

public class NamespaceGenerator implements ICodeGenRule<NamespaceNode>
{
    @Override
    public Result<Void> generate(NamespaceNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Open Namespace Directory
        context.openSubdirectory(component.identifier.value);
        
        // Generate Body
        result.register(context.generate(component.body));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
