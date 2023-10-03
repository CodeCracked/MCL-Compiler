package compiler.core.codegen;

import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;

public abstract class DataTypeCodeAdapter
{
    public abstract Result<Void> resetVariable(VariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyFromRegister(int register, VariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyToRegister(int register, VariableSymbol variable, CodeGenContext context);
    public abstract Result<Void> copyToRegister(int register, Object literal, CodeGenContext context);
    public abstract Result<Void> add(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> subtract(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> multiply(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> divide(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> modulo(int accumulatorRegister, int argumentRegister, CodeGenContext context);
    public abstract Result<Void> compare(int argument1Register, int argument2Register, int destinationRegister, CodeGenContext context);
}
