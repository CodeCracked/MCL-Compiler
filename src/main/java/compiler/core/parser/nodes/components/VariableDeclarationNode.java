package compiler.core.parser.nodes.components;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.annotations.OptionalChild;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.exceptions.DuplicateSymbolException;

public abstract class VariableDeclarationNode<T extends VariableSymbol> extends AbstractNode
{
    public final DataTypeNode type;
    public final IdentifierNode identifier;
    @OptionalChild(alwaysShow = false) public AbstractValueNode initialValue;
    
    private VariableSymbol symbol;
    
    public VariableDeclarationNode(SourcePosition start, SourcePosition end, DataTypeNode type, IdentifierNode identifier, AbstractValueNode initialValue)
    {
        super(start, end);
        this.type = type;
        this.identifier = identifier;
        this.initialValue = initialValue;
    }
    
    protected abstract Result<T> createSymbol();
    
    public VariableSymbol getSymbol() { return symbol; }
    
    @Override
    protected Result<Void> createSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Create Symbol
        symbol = result.register(createSymbol());
        if (result.getFailure() != null) return result;
        
        // Register Symbol
        if (!symbolTable().addSymbolWithUniqueName(this, symbol, true)) return result.failure(new DuplicateSymbolException(this, symbol));
        
        return result.success(null);
    }
    
    @Override
    protected Result<Void> validate()
    {
        if (initialValue != null && initialValue.getValueType() != type.value)
        {
            Result<Void> result = new Result<>();
            
            if (initialValue.getValueType().canImplicitCast(type.value))
            {
                AbstractValueNode casted = result.register(initialValue.cast(type));
                if (result.getFailure() != null) return result;
    
                initialValue = casted;
                return result.success(null);
            }
            else return result.failure(new CompilerException(start(), end(), "Cannot assign variable of type '" + type.value.name() + "' to a value of type '" + initialValue.getValueType().name() + "'!"));
        }
        else return Result.of(null);
    }
}
