package mcl.parser.nodes.components;

import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.parser.symbols.types.AbstractVariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.parser.symbols.VariableSymbol;
import mcl.util.Lookups;

import java.util.Optional;

public class VariableAccessNode extends AbstractValueNode
{
    public final QualifiedIdentifierNode identifier;
    
    private VariableSymbol symbol;
    
    public VariableAccessNode(QualifiedIdentifierNode identifier)
    {
        super(identifier.start(), identifier.end());
        this.identifier = identifier;
    }
    
    public AbstractVariableSymbol getSymbol() { return symbol; }
    
    @Override
    protected Result<Void> retrieveSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Assign variable symbol
        SymbolTable.SymbolEntry<VariableSymbol> lookup = result.register(Lookups.variable(this, identifier));
        if (result.getFailure() != null) return result;
        symbol = lookup.symbol();
        
        return result.success(null);
    }
    
    @Override
    public DataType getValueType()
    {
        return symbol.getType();
    }
    
    @Override
    public Optional<Object> getValue()
    {
        return Optional.empty();
    }
}
