package mcl.util;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.symbols.VariableSymbol;

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
}
