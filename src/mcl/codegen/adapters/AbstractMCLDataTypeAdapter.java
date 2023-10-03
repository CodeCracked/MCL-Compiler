package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeCodeAdapter;
import compiler.core.util.Result;

import java.io.PrintWriter;

public abstract class AbstractMCLDataTypeAdapter extends DataTypeCodeAdapter
{
    protected Result<Void> writeCommand(CodeGenContext context, String format, Object... args)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Write Copy
        file.printf(format, args);
        file.println();
        
        return result.success(null);
    }
}
