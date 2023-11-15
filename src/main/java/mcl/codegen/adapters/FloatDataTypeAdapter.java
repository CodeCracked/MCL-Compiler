package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.symbols.types.AbstractVariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.lexer.MCLDataTypes;
import mcl.parser.symbols.VariableSymbol;

import java.io.PrintWriter;

public class FloatDataTypeAdapter extends AbstractMCLDataTypeAdapter
{
    public FloatDataTypeAdapter()
    {
        super(MCLDataTypes.FLOAT);
    }
    
    //region AbstractMCLDataTypeAdapter Implementation
    @Override
    protected Result<Void> copyRegisterToNbt(int register, String nbtKey, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Copy Float Registers to Math IO
        file.printf("scoreboard players operation P0 mcl.math.io = r%1$d.s mcl.registers\n", register);
        file.printf("scoreboard players operation P1 mcl.math.io = r%1$d.e mcl.registers\n", register);
        file.printf("scoreboard players operation P2 mcl.math.io = r%1$d.m mcl.registers\n", register);
        
        // Recompose
        file.println("function mcl:math/float/32/recompose/main");
        
        // Store 32-Bit Float
        file.println("execute store result storage mcl:runtime " + nbtKey + " int 1 run scoreboard players get R0 mcl.math.io");
        
        return result.success(null);
    }
    
    @Override
    protected Result<Void> copyNbtToRegister(int register, String nbtKey, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Retrieve 32-Bit Float
        file.printf("execute store result score P0 mcl.math.io run data get storage mcl:runtime %1$s 1\n", nbtKey);
        
        // Decompose
        file.printf("function mcl:math/float/32/decompose/main\n");
        
        // Copy Math IO to Float Registers
        file.printf("scoreboard players operation r%1$d.s mcl.registers = R0 mcl.math.io\n", register);
        file.printf("scoreboard players operation r%1$d.e mcl.registers = R1 mcl.math.io\n", register);
        file.printf("scoreboard players operation r%1$d.m mcl.registers = R2 mcl.math.io\n", register);
        
        return result.success(null);
    }
    
    //endregion
    //region DataTypeAdapter Implementation
    @Override
    public Result<Void> cast(int register, DataType castTo, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // float -> int casting
        if (castTo == MCLDataTypes.INTEGER)
        {
            file.printf("scoreboard players operation P0 mcl.math.io = r%1$d.s mcl.registers\n", register);
            file.printf("scoreboard players operation P1 mcl.math.io = r%1$d.e mcl.registers\n", register);
            file.printf("scoreboard players operation P2 mcl.math.io = r%1$d.m mcl.registers\n", register);
            file.printf("function mcl:math/float/32/convert/to_storage/main\n");
            file.printf("execute store result score r%1$d mcl.registers run data get storage mcl:math R0 1\n", register);
            return result.success(null);
        }
        
        return result.failure(new UnsupportedOperationException("Cannot cast " + getType().name() + " to " + castTo.name() + "!"));
    }
    
    @Override
    public Result<Void> resetVariable(AbstractVariableSymbol variable, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
    
        // Write Command
        file.println("data modify storage mcl:runtime " + ((VariableSymbol) variable).getNBTKey() + " set value " + Float.floatToRawIntBits(0.0f));
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyVariableToRegister(int register, AbstractVariableSymbol variable, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
    
        // Copy Variable to Math IO
        file.printf("execute store result score P0 mcl.math.io run data get storage mcl:runtime " + ((VariableSymbol) variable).getNBTKey() + " 1\n");
        
        // Decompose
        file.println("function mcl:math/float/32/decompose/main");
        
        // Copy Float Components to Registers
        file.printf("scoreboard players operation r%1$d.s mcl.registers = R0 mcl.math.io\n", register);
        file.printf("scoreboard players operation r%1$d.e mcl.registers = R1 mcl.math.io\n", register);
        file.printf("scoreboard players operation r%1$d.m mcl.registers = R2 mcl.math.io\n", register);
    
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyLiteralToRegister(int register, Object literal, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Calculate Float Components
        int floatBits = Float.floatToRawIntBits((Float)literal);
        int mantissa = floatBits & 0x007fffff;
        int exponent = (floatBits & 0x7f800000) >> 23;
        int sign = (floatBits & 0x80000000) >> 31;
        
        // Set Float Registers
        file.printf("scoreboard players set r%1$d.s mcl.registers %2$d\n", register, sign);
        file.printf("scoreboard players set r%1$d.e mcl.registers %2$d\n", register, exponent - 127);
        file.printf("scoreboard players set r%1$d.m mcl.registers %2$d\n", register, mantissa);
        
        return result.success(null);
    }
    
    @Override
    public Result<Void> add(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(accumulatorRegister, argumentRegister, "add", context);
    }
    
    @Override
    public Result<Void> subtract(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(accumulatorRegister, argumentRegister, "subtract", context);
    }
    
    @Override
    public Result<Void> multiply(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(accumulatorRegister, argumentRegister, "multiply", context);
    }
    
    @Override
    public Result<Void> divide(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return writeOperation(accumulatorRegister, argumentRegister, "divide", context);
    }
    
    @Override
    public Result<Void> modulo(int accumulatorRegister, int argumentRegister, CodeGenContext context)
    {
        return Result.fail(new UnsupportedOperationException("FloatDataTypeAdapter.modulo not supported!"));
    }
    
    @Override
    public Result<Void> negate(int accumulatorRegister, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Flip Sign Component
        file.printf("scoreboard players remove r%1$d.s mcl.registers 1\n", accumulatorRegister);
        file.printf("execute if score r%1$d.s mcl.registers matches ..-1 run scoreboard players set r%1$d.s mcl.registers 1\n", accumulatorRegister);
        
        return result.success(null);
    }
    
    @Override
    public Result<Void> compare(int argument1Register, int argument2Register, int destinationRegister, CodeGenContext context)
    {
        return Result.fail(new UnsupportedOperationException("FloatDataTypeAdapter.compare not supported!"));
    }
    //endregion
    //region Private Helpers
    private Result<Void> writeOperation(int accumulatorRegister, int argumentRegister, String operationFunction, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Copy Accumulator to Math IO
        file.printf("scoreboard players operation P0 mcl.math.io = r%1$d.s mcl.registers\n", accumulatorRegister);
        file.printf("scoreboard players operation P1 mcl.math.io = r%1$d.e mcl.registers\n", accumulatorRegister);
        file.printf("scoreboard players operation P2 mcl.math.io = r%1$d.m mcl.registers\n", accumulatorRegister);
        
        // Copy Argument to Math IO
        file.printf("scoreboard players operation P3 mcl.math.io = r%1$d.s mcl.registers\n", argumentRegister);
        file.printf("scoreboard players operation P4 mcl.math.io = r%1$d.e mcl.registers\n", argumentRegister);
        file.printf("scoreboard players operation P5 mcl.math.io = r%1$d.m mcl.registers\n", argumentRegister);
        
        // Operation
        file.println("function mcl:math/float/32/" + operationFunction + "/main");
        
        // Copy Math IO to Accumulator
        file.printf("scoreboard players operation r%1$d.s mcl.registers = R0 mcl.math.io\n", accumulatorRegister);
        file.printf("scoreboard players operation r%1$d.e mcl.registers = R1 mcl.math.io\n", accumulatorRegister);
        file.printf("scoreboard players operation r%1$d.m mcl.registers = R2 mcl.math.io\n", accumulatorRegister);
        
        return result.success(null);
    }
    //endregion
}
