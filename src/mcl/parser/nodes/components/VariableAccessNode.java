package mcl.parser.nodes.components;

import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;

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
    
    public VariableSymbol getSymbol() { return symbol; }
    
    @Override
    protected Result<Void> retrieveSymbols()
    {
        Result<Void> result = new Result<>();
    
        // Lookup namespace table
        SymbolTable table = symbolTable();
        if (identifier.qualified)
        {
            result.register(symbolTable().root().getChildTable(identifier.namespace.value));
            if (result.getFailure() != null) return result;
        }
        
        // Lookup variable symbol
        SymbolTable.SymbolEntry<VariableSymbol> lookup = result.register(table.lookupByName(this, VariableSymbol.class, identifier.identifier.value).single());
        if (result.getFailure() != null) return result;
        
        // Assign variable symbol
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
