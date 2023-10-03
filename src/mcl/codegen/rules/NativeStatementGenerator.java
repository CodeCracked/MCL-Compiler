package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.statements.NativeStatementNode;

import java.io.IOException;
import java.io.PrintWriter;

public class NativeStatementGenerator implements ICodeGenRule<NativeStatementNode>
{
    @Override
    public Result<Void> generate(NativeStatementNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
    
        // Write Native Commands
        file.println("# Native Commands: " + component.start().toString());
        for (String command : component.nativeCommands) file.println(command);
        file.println();
    
        return result.success(null);
    }
}
