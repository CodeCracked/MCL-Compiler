package mcl.util;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UndefinedSymbolException;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.symbols.FunctionSymbol;
import mcl.parser.symbols.VariableSymbol;

import java.util.Optional;

public final class Lookups
{
    public static Result<SymbolTable.SymbolEntry<VariableSymbol>> variable(AbstractNode scope, QualifiedIdentifierNode identifier) { return variable(scope, identifier.qualified ? identifier.namespace.value : null, identifier.identifier.value); }
    public static Result<SymbolTable.SymbolEntry<VariableSymbol>> variable(AbstractNode scope, String namespace, String identifier)
    {
        Result<SymbolTable.SymbolEntry<VariableSymbol>> result = new Result<>();
        
        // Lookup namespace table
        if (namespace != null)
        {
            result.register(scope.symbolTable().root().getChildTable(namespace));
            if (result.getFailure() != null) return result;
        }
    
        // Lookup variable symbol
        SymbolTable.SymbolEntry<VariableSymbol> lookup = result.register(scope.symbolTable().lookupByName(scope, VariableSymbol.class, identifier).single());
        if (result.getFailure() != null) return result;
        
        return result.success(lookup);
    }
    
    public static <T extends FunctionSymbol<?>> Result<SymbolTable.SymbolEntry<T>> function(Class<T> clazz, AbstractNode scope, QualifiedIdentifierNode identifier)
    {
        Result<SymbolTable.SymbolEntry<T>> result = new Result<>();
        
        // Get Namespace Table
        Optional<SymbolTable> namespaceTable = scope.symbolTable().root().tryGetChildTable(identifier.namespace.value);
        if (namespaceTable.isEmpty()) return result.failure(new UndefinedSymbolException(identifier.namespace, "namespace", " '" + identifier.namespace.value + "'"));
    
        // Get Function Symbol
        SymbolTable.SymbolEntry<T> lookup = result.register(namespaceTable.get().lookupByName(identifier.identifier, clazz, identifier.identifier.value).single());
        if (result.getFailure() != null) return result;
        
        return result.success(lookup);
    }
}
