package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.NamespaceNode;

import java.io.PrintWriter;

import static java.lang.String.format;

public class MCLNumberDataTypeAdapter extends AbstractMCLDataTypeAdapter
{
    private final float toScale;
    private final float fromScale;
    
    //region Creation
    private MCLNumberDataTypeAdapter(float toScale, float fromScale)
    {
        this.toScale = toScale;
        this.fromScale = fromScale;
    }
    public static MCLNumberDataTypeAdapter integer() { return new MCLNumberDataTypeAdapter(1, 1); }
    public static MCLNumberDataTypeAdapter decimal(int precision) { return new MCLNumberDataTypeAdapter((float)Math.pow(10, precision), (float)Math.pow(10, -precision)); }
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
        file.println(" " + fromScale + " run scoreboard players get r" + register + " mcl.registers");
        
        return result.success(null);
    }
    
    @Override
    public Result<Void> copyToRegister(int register, VariableSymbol variable, CodeGenContext context)
    {
        return writeCommand(context, "# MCLNumberDataTypeAdapter.copyToRegister(int, VariableSymbol, CodeGenContext) is not implemented!");
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
