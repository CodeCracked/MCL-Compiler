package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.parser.symbols.types.AbstractVariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.parser.symbols.VariableSymbol;

import java.io.PrintWriter;

public abstract class AbstractMCLDataTypeAdapter extends DataTypeAdapter
{
    public AbstractMCLDataTypeAdapter(DataType type)
    {
        super(type);
    }
    
    protected abstract Result<Void> copyRegisterToNbt(int register, String nbtKey, CodeGenContext context);
    protected abstract Result<Void> copyNbtToRegister(int register, String nbtKey, CodeGenContext context);
    
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
    
    @Override
    public Result<Void> copyRegisterToVariable(int register, AbstractVariableSymbol variable, CodeGenContext context)
    {
        return copyRegisterToNbt(register, ((VariableSymbol) variable).getNBTKey(), context);
    }
    
    @Override
    public Result<Void> copyRegisterToReturn(int register, CodeGenContext context)
    {
        return copyRegisterToNbt(register, "Return", context);
    }
    
    @Override
    public Result<Void> copyReturnToRegister(int register, CodeGenContext context)
    {
        return copyNbtToRegister(register, "Return", context);
    }
}
