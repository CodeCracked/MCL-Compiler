package compiler.core.codegen;

import compiler.core.parser.symbols.types.AbstractVariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;

public abstract class DataTypeAdapter
{
    private final DataType type;
    
    public DataTypeAdapter(DataType type) { this.type = type; }
    
    public DataType getType() { return type; }
    
    public abstract Result<Void> cast(int register, DataType castTo, CodeGenContext context);
    public abstract Result<Void> resetVariable(AbstractVariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyRegisterToVariable(int register, AbstractVariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyRegisterToReturn(int register, CodeGenContext context);
    public abstract Result<Void> copyVariableToRegister(int register, AbstractVariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyLiteralToRegister(int register, Object literal, CodeGenContext context);
    public abstract Result<Void> copyReturnToRegister(int register, CodeGenContext context);
    public abstract Result<Void> add(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> subtract(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> multiply(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> divide(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> modulo(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> negate(int accumulatorRegister, CodeGenContext context);
    public abstract Result<Void> compare(int argument1Register, int argument2Register, int destinationRegister, CodeGenContext context);
}
