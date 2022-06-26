package mcl.compiler.analyzer;

import mcl.compiler.analyzer.symbols.EventSymbol;
import mcl.compiler.exceptions.MCLDuplicateSymbolError;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLUndefinedSymbolError;
import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SymbolTable
{
    public final int depth;
    public final SymbolTable parent;

    private final Object id;
    private final MCLSourceCollection source;
    private final Map<SymbolType, Map<String, Symbol>> symbolMap;
    private final Set<Symbol> forgivableDuplicatesSet = new HashSet<>();
    private final Map<Object, SymbolTable> childTables;

    public SymbolTable(MCLSourceCollection source, SymbolTable parent, Object id)
    {
        this.depth = parent == null ? 0 : parent.depth + 1;
        this.parent = parent;

        this.id = id;
        this.source = source;
        this.symbolMap = new HashMap<>();
        this.childTables = new HashMap<>();
    }
    public SymbolTable addRootSymbols()
    {
        //addSymbol(new EventSymbol(Token.description(TokenType.IDENTIFIER, "minecraft:load")));
        //addSymbol(new EventSymbol(Token.description(TokenType.IDENTIFIER, "minecraft:tick")));
        return this;
    }

    public Symbol getSymbol(String identifier, SymbolType symbolType)
    {
        if (symbolMap.containsKey(symbolType) && symbolMap.get(symbolType).containsKey(identifier)) return symbolMap.get(symbolType).get(identifier);
        else if (parent != null) return parent.getSymbol(identifier, symbolType);
        else return null;
    }
    public MCLError checkSymbolDefinition(Token identifier, SymbolType symbolType)
    {
        if (getSymbol((String)identifier.value(), symbolType) != null) return null;
        else return new MCLUndefinedSymbolError(source, identifier, symbolType);
    }
    //public MCLError addSymbol(Symbol symbol) { return addSymbol(symbol, false); }
    public MCLError addSymbol(Symbol symbol)
    {
        String name = (String)symbol.identifier.value();
        boolean forgiveDuplicate = source.openHeader != null;

        if (symbolMap.containsKey(symbol.symbolType) && symbolMap.get(symbol.symbolType).containsKey(name))
        {
            Symbol existing = symbolMap.get(symbol.symbolType).get(name);
            boolean forgive = forgivableDuplicatesSet.contains(symbol) || forgiveDuplicate;

            if (!forgive) return new MCLDuplicateSymbolError(source, symbol.identifier, symbol.symbolType);
            else if (symbol.symbolType == SymbolType.EVENT && forgive)
            {
                EventSymbol a = (EventSymbol) symbol;
                EventSymbol b = (EventSymbol) existing;
                b.listenerFunctionFiles.addAll(a.listenerFunctionFiles);

                if (!forgiveDuplicate) forgivableDuplicatesSet.remove(existing);
                return null;
            }
            else if (symbol.symbolType == SymbolType.NAMESPACE)
            {
                if (!forgiveDuplicate) forgivableDuplicatesSet.remove(existing);
                return null;
            }
            else return new MCLDuplicateSymbolError(source, symbol.identifier, symbol.symbolType);
        }
        else
        {
            String location = (String)symbol.identifier.value();
            SymbolTable current = this;
            while (current != null)
            {
                location = current.id.toString() + "." + location;
                current = current.parent;
            }
            symbol.tableLocation = location;

            if (!symbolMap.containsKey(symbol.symbolType)) symbolMap.put(symbol.symbolType, new HashMap<>());
            symbolMap.get(symbol.symbolType).put(name, symbol);
            if (forgiveDuplicate) forgivableDuplicatesSet.add(symbol);
            return null;
        }
    }

    public void forEach(SymbolType symbolType, Consumer<Symbol> consumer)
    {
        if (symbolMap.containsKey(symbolType)) symbolMap.get(symbolType).values().forEach(consumer);
    }
    public void forEach(Consumer<Symbol> consumer)
    {
        symbolMap.values().forEach(map -> map.values().forEach(consumer));
    }

    public SymbolTable getOrCreateChildTable(Object id)
    {
        if (!childTables.containsKey(id)) childTables.put(id, new SymbolTable(source, this, id));
        return childTables.get(id);
    }
}
