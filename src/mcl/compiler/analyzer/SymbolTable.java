package mcl.compiler.analyzer;

import mcl.compiler.exceptions.MCLDuplicateSymbolError;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLUndefinedSymbolError;
import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class SymbolTable
{
    public final int depth;
    public final SymbolTable parent;

    private final UUID id;
    private final MCLSourceCollection source;
    private final Map<SymbolType, Map<String, Symbol>> symbolMap;
    private final Map<UUID, SymbolTable> childTables;

    public SymbolTable(MCLSourceCollection source, SymbolTable parent, UUID id)
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
    public MCLError addSymbol(Symbol symbol)
    {
        String name = (String)symbol.identifier.value();
        if (symbolMap.containsKey(symbol.symbolType) && symbolMap.get(symbol.symbolType).containsKey(name)) return new MCLDuplicateSymbolError(source, symbol.identifier, symbol.symbolType);
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

    public SymbolTable getOrCreateChildTable(UUID id)
    {
        if (!childTables.containsKey(id)) childTables.put(id, new SymbolTable(source, this, id));
        return childTables.get(id);
    }
}
