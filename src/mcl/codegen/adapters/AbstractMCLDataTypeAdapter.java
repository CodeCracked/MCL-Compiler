package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeCodeAdapter;
import compiler.core.util.Result;

import java.io.PrintWriter;

import static java.text.MessageFormat.format;

public abstract class AbstractMCLDataTypeAdapter extends DataTypeCodeAdapter
{
    protected Result<Void> writeCommand(CodeGenContext context, String format, Object... args)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Write Copy
        file.println(format(format, args));
        
        return result.success(null);
    }
    protected Result<Void> writeOperation(CodeGenContext context, String operation, int accumulatorRegister, int argumentRegister)
    {
        return writeCommand(context, "scoreboard players operation r{0} mcl.registers {2} r{1} mcl.registers", accumulatorRegister, argumentRegister, operation);
    }
    
    @Override
    public Result<Void> add(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(context, "+=", accumulatorRegister, argumentRegister);
    }
    
    @Override
    public Result<Void> subtract(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(context, "-=", accumulatorRegister, argumentRegister);
    }
    
    @Override
    public Result<Void> multiply(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(context, "*=", accumulatorRegister, argumentRegister);
    }
    
    @Override
    public Result<Void> divide(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(context, "/=", accumulatorRegister, argumentRegister);
    }
    
    @Override
    public Result<Void> modulo(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(context, "%=", accumulatorRegister, argumentRegister);
    }
    
    @Override
    public Result<Void> compare(int argument1Register, int argument2Register, int destinationRegister, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Write Comparison
        file.println(format("scoreboard players operation r{0} mcl.registers = r{1} mcl.registers", destinationRegister, argument1Register));
        file.println(format("scoreboard players operation r{0} mcl.registers -= r{1} mcl.registers", destinationRegister, argument2Register));
        
        return result.success(null);
    }
}
