package compiler.core.parser.nodes.components;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.annotations.OptionalChild;
import compiler.core.util.exceptions.DuplicateSymbolException;

public class VariableDeclarationNode extends AbstractNode
{
    public final DataTypeNode type;
    public final IdentifierNode identifier;
    @OptionalChild(alwaysShow = false) public final AbstractValueNode initialValue;
    
    private VariableSymbol symbol;
    
    public VariableDeclarationNode(SourcePosition start, SourcePosition end, DataTypeNode type, IdentifierNode identifier, AbstractValueNode initialValue)
    {
        super(start, end);
        this.type = type;
        this.identifier = identifier;
        this.initialValue = initialValue;
    }
    
    public VariableSymbol getSymbol() { return symbol; }
    
    @Override
    protected Result<Void> createSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Create Symbol
        if (initialValue != null) symbol = result.register(VariableSymbol.initialized(this, identifier.value, type.value, initialValue));
        else symbol = VariableSymbol.uninitialized(this, identifier.value, type.value);
        if (result.getFailure() != null) return result;
        
        // Register Symbol
        if (!symbolTable().addSymbolWithUniqueName(this, symbol, true)) return result.failure(new DuplicateSymbolException(this, symbol));
        return result.success(null);
    }
}