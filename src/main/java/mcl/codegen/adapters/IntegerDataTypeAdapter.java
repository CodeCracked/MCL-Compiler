package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.NamespaceNode;

import java.io.PrintWriter;

import static java.lang.String.format;

public class IntegerDataTypeAdapter extends AbstractMCLDataTypeAdapter
{
    public IntegerDataTypeAdapter()
    {
        super(MCLDataTypes.INTEGER);
    }
    
    //region Casting
    @Override
    public Result<Void> cast(int register, DataType castTo, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // int -> float casting
        if (castTo == MCLDataTypes.FLOAT)
        {
            file.printf("scoreboard players operation P0 mcl.math.io = r%1$d mcl.registers\n", register);
            file.printf("function mcl:math/float/32/convert/from_int/main\n");
            file.printf("scoreboard players operation r%1$d.s = R0 mcl.math.io\n", register);
            file.printf("scoreboard players operation r%1$d.e = R1 mcl.math.io\n", register);
            file.printf("scoreboard players operation r%1$d.m = R2 mcl.math.io\n", register);
            return result.success(null);
        }
        
        return result.failure(new UnsupportedOperationException("Cannot cast " + getType().name() + " to " + castTo.name() + "!"));
    }
    //endregion
    //region Implementation
    @Override
    public Result<Void> resetVariable(VariableSymbol variable, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Get Namespace
        NamespaceNode namespace = result.register(variable.definition().findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        
        // Write Command
        String nbtKey = namespace.identifier.value + "_" + variable.name();
        file.println("data modify storage mcl:runtime " + variable.getCallStackKey() + "." + nbtKey + " set value 0");
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyFromRegister(int register, VariableSymbol variable, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Get Namespace
        NamespaceNode namespace = result.register(variable.definition().findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        
        // Write Command
        String nbtKey = namespace.identifier.value + "_" + variable.name();
        file.println("execute store result storage mcl:runtime " + variable.getCallStackKey() + "." + nbtKey + " int 1 run scoreboard players get r" + register + " mcl.registers");
        
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyToRegister(int register, VariableSymbol variable, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Get Namespace
        NamespaceNode namespace = result.register(variable.definition().findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        
        // Write Command
        String nbtKey = namespace.identifier.value + "_" + variable.name();
        file.printf("execute store result score r%1$d mcl.registers run data get storage mcl:runtime " + variable.getCallStackKey() + ".%2$s 1", register, nbtKey);
        file.println();

        return result.success(null);
    }
    
    @Override
    public Result<Void> copyToRegister(int register, Object literal, CodeGenContext context)
    {
        return writeCommand(context, "scoreboard players set r%1$d mcl.registers %2$d", register, literal);
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
        return writeOperation(context, "+=", accumulatorRegister, argumentRegister);
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
        file.println(format("scoreboard players operation r%1$d mcl.registers = r%2$d mcl.registers", destinationRegister, argument1Register));
        file.println(format("scoreboard players operation r%1$d mcl.registers -= r%2$d mcl.registers", destinationRegister, argument2Register));
        
        return result.success(null);
    }
    //endregion
    //region Private Helpers
    private Result<Void> writeOperation(CodeGenContext context, String operation, int accumulatorRegister, int argumentRegister)
    {
        return writeCommand(context, "scoreboard players operation r%1$d mcl.registers %3$s r%2$d mcl.registers", accumulatorRegister, argumentRegister, operation);
    }
    //endregion
}
