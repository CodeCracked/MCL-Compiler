package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.MCL;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.NamespaceNode;

import java.io.PrintWriter;

import static java.lang.String.format;

public abstract class NumericDataTypeAdapter extends AbstractMCLDataTypeAdapter
{
    private final float toScale;
    private final float fromScale;
    private final int decimalPlaces;
    
    //region Creation
    protected NumericDataTypeAdapter(DataType type, int decimalPlaces)
    {
        super(type);
        this.toScale = (float)Math.pow(10, decimalPlaces);
        this.fromScale = (float)Math.pow(10, -decimalPlaces);
        this.decimalPlaces = decimalPlaces;
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
        file.print("data modify storage mcl:runtime CallStack[0]." + nbtKey + " set value ");
        if (variable.getType() == MCLDataTypes.INTEGER) file.println("0");
        else if (variable.getType() == MCLDataTypes.FLOAT) file.println("0.0f");
        else return Result.fail(new IllegalStateException("MCLNumberDataTypeAdapter only supports INTEGER or FLOAT DataTypes, found " + variable.getType().name() + "!"));
    
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
        file.print("execute store result storage mcl:runtime CallStack[0]." + nbtKey + " ");
        if (variable.getType() == MCLDataTypes.INTEGER) file.print("int");
        else if (variable.getType() == MCLDataTypes.FLOAT) file.print("float");
        else return Result.fail(new IllegalStateException("MCLNumberDataTypeAdapter only supports INTEGER or FLOAT DataTypes, found " + variable.getType().name() + "!"));
        file.printf(" %." + decimalPlaces + "f run scoreboard players get r" + register + " mcl.registers", fromScale);
        file.println();
        
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
        file.printf("execute store result score r%1$d mcl.registers run data get storage mcl:runtime CallStack[0].%2$s ", register, nbtKey);
        file.printf("%." + decimalPlaces + "f", toScale);
        file.println();
        
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyToRegister(int register, Object literal, CodeGenContext context)
    {
        float value = toScale;
        if (literal instanceof Integer i) value *= i;
        else if (literal instanceof Float f) value *= f;
        else return Result.fail(new IllegalStateException("MCLNumberDataTypeAdapter only supports Integer or Float literals, found " + literal.getClass().getSimpleName() + "!"));
        
        return writeCommand(context, "scoreboard players set r%1$d mcl.registers %2$d", register, Math.round(value));
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
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Write Operation
        result.register(writeOperation(context, "*=", accumulatorRegister, argumentRegister));
        if (result.getFailure() != null) return result;
    
        // Write Conversion Factor
        file.printf("scoreboard players set rTemp mcl.registers 1");
        for (int i = 0; i < MCL.FLOAT_DECIMAL_PLACES; i++) file.print('0');
        file.println();
        
        // Divide by conversion factor to prevent scale increase
        file.printf("scoreboard players operation r%1$d mcl.registers /= rTemp mcl.registers", accumulatorRegister);
        file.println();
        
        return result.success(null);
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
