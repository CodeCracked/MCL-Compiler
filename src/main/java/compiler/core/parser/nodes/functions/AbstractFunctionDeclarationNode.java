package compiler.core.parser.nodes.functions;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.parser.symbols.types.AbstractFunctionSymbol;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.DuplicateSymbolException;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.symbols.EventSymbol;

public abstract class AbstractFunctionDeclarationNode<T extends AbstractFunctionSymbol> extends AbstractNode
{
    public final FunctionSignatureNode signature;
    
    protected SymbolTable childTable;
    
    public AbstractFunctionDeclarationNode(SourcePosition start, SourcePosition end, FunctionSignatureNode signature)
    {
        super(start, end);
        this.signature = signature;
    }
    
    protected abstract Result<T> instantiateSymbol();
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Function " + signature.identifier.value);
        return childTable;
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        Result<Void> result = new Result<>();
    
        // Create Parameter Symbols
        result.register(signature.parameters.createParameterSymbols());
        if (result.getFailure() != null) return result;
        
        // Create Function Symbol
        T symbol = result.register(instantiateSymbol());
        
        // TODO: Allow Function Overloading
        if (!symbolTable().addSymbolWithUniqueName(signature.identifier, symbol, false)) return result.failure(new DuplicateSymbolException(signature.identifier, symbol));
        else return result.success(null);
    }
}
