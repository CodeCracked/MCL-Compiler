package mcl.codegen.rules.nodes.methods;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.declarations.MethodDeclarationNode;
import mcl.util.MCLCodeGenMacros;

import java.io.IOException;

public class MethodDeclarationGenerator implements ICodeGenRule<MethodDeclarationNode>
{
    @Override
    public Result<Void> generate(MethodDeclarationNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
    
        // Open Method File
        context.openSubdirectory("functions", "methods", component.functionName());
        context.openFile("main.mcfunction", file ->
        {
            MCLCodeGenMacros.writeNodeHeader(component, file);
            file.println();
        });
    
        // Generate Body
        result.register(context.generate(component.body));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
