package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;

import java.io.IOException;

public class MCLSourceGenerator implements ICodeGenRule<MCLSourceNode>
{
    @Override
    public Result<Void> generate(MCLSourceNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Generate Namespaces
        for (NamespaceNode namespace : component.namespaces)
        {
            result.register(context.getGenerator().generate(namespace, context));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(null);
    }
}
