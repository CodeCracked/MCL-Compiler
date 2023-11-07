package mcl.parser.nodes.components;

import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.parser.symbols.FunctionSymbol;
import mcl.util.Lookups;

import java.util.Optional;

public class FunctionCallNode extends AbstractValueNode
{
    public final QualifiedIdentifierNode function;
    public final ArgumentListNode arguments;
    
    private SymbolTable.SymbolEntry<FunctionSymbol> functionSymbol;
    
    public FunctionCallNode(QualifiedIdentifierNode function, ArgumentListNode arguments)
    {
        super(function.start(), arguments.end());
        this.function = function;
        this.arguments = arguments;
    }
    
    public FunctionSymbol getSymbol() { return functionSymbol.symbol(); }
    
    @Override
    public Optional<Object> getValue()
    {
        return Optional.empty();
    }
    
    @Override
    public DataType getValueType()
    {
        return functionSymbol.symbol().signature.returnType.value;
    }
    
    @Override
    protected Result<Void> retrieveSymbols()
    {
        Result<Void> result = new Result<>();
    
        // Get Symbol
        this.functionSymbol = result.register(Lookups.function(FunctionSymbol.class, this, function));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
