package mcl.codegen.rules.nodes.functions;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.declarations.FunctionDeclarationNode;
import mcl.util.MCLCodeGenMacros;

import java.io.IOException;

public class FunctionDeclarationGenerator implements ICodeGenRule<FunctionDeclarationNode>
{
    @Override
    public Result<Void> generate(FunctionDeclarationNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
    
        // Open Function File
        context.openSubdirectory("functions", "functions", component.functionName());
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
