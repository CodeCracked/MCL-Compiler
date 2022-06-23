package mcl.compiler.analyzer;

import mcl.compiler.exceptions.MCLDuplicateSymbolError;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLUndefinedSymbolError;
import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable
{
    public final int depth;
    public final SymbolTable parent;

    private final MCLSourceCollection source;
    private final Map<String, Map<SymbolType, Symbol>> symbolMap;
    private final Map<String, SymbolTable> childTables;

    public SymbolTable(MCLSourceCollection source, SymbolTable parent)
    {
        this.depth = parent == null ? 0 : parent.depth + 1;
        this.parent = parent;
        this.source = source;
        this.symbolMap = new HashMap<>();
        this.childTables = new HashMap<>();
    }

    public Symbol getSymbol(String identifier, SymbolType symbolType)
    {
        if (symbolMap.containsKey(identifier) && symbolMap.get(identifier).containsKey(symbolType)) return symbolMap.get(identifier).get(symbolType);
        else if (parent != null) return parent.getSymbol(identifier, symbolType);
        else return null;
    }
    public MCLError checkSymbolDefinition(Token identifier, SymbolType symbolType)
    {
        if (getSymbol((String)identifier.value(), symbolType) != null) return null;
        else return new MCLUndefinedSymbolError(source, identifier, symbolType);
    }
    public MCLError addSymbol(Symbol symbol)
    {
        String name = (String)symbol.identifier.value();
        if (symbolMap.containsKey(name) && symbolMap.get(name).containsKey(symbol.symbolType)) return new MCLDuplicateSymbolError(source, symbol.identifier, symbol.symbolType);
        else
        {
            if (!symbolMap.containsKey(name)) symbolMap.put(name, new HashMap<>());
            symbolMap.get(name).put(symbol.symbolType, new Symbol(symbol.identifier, symbol.symbolType));
            return null;
        }
    }

    public SymbolTable getOrCreateChildTable(String name)
    {
        if (!childTables.containsKey(name)) childTables.put(name, new SymbolTable(source, this));
        return childTables.get(name);
    }
}
