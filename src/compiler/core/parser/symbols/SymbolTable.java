package compiler.core.parser.symbols;

import compiler.core.util.IO;
import compiler.core.util.Ref;

import java.util.*;
import java.util.function.Predicate;

public class SymbolTable
{
    public static final class Lookup<T extends AbstractSymbol>
    {
        private final T symbol;
        private final int parentLevel;
        private final SymbolTable lookupTable;
        private final SymbolTable ownerTable;
        
        private Lookup(T symbol, int parentLevel, SymbolTable lookupTable, SymbolTable ownerTable)
        {
            this.symbol = symbol;
            this.parentLevel = parentLevel;
            this.lookupTable = lookupTable;
            this.ownerTable = ownerTable;
        }
        
        public T symbol() { return symbol; }
        public int parentLevel() { return parentLevel; }
        public SymbolTable lookupTable() { return lookupTable; }
        public SymbolTable ownerTable() { return ownerTable; }
    }
    
    private final String name;
    private final SymbolTable parent;
    private final SymbolTable root;
    private final Map<Class<? extends AbstractSymbol>, List<? extends AbstractSymbol>> localSymbols;
    private final Map<String, SymbolTable> childTables;
    
    private SymbolTable(String name, SymbolTable parent, SymbolTable root)
    {
        this.name = name;
        this.parent = parent;
        this.root = root != null ? root : this;
        this.localSymbols = new HashMap<>();
        this.childTables = new HashMap<>();
    }
    
    public void debugPrint()
    {
        IO.Debug.println(name);
        for (SymbolTable child : childTables.values()) child.debugPrint();
    }
    
    private static <T extends AbstractSymbol> List<T> newSymbolList(Class<T> clazz) { return new ArrayList<>(); }
    
    //region Child Tables
    public static SymbolTable createRootTable() { return new SymbolTable("ROOT", null, null); }
    public SymbolTable createChildTable(String name) { return createChildTable(Ref.of(name)); }
    public SymbolTable createChildTable(Ref<String> name)
    {
        String nameRoot = name.get();
        int i = 1;
        while (childTables.containsKey(name.get())) name.set(nameRoot + " (" + i++ + ")");
        return getOrCreateChildTable(name.get());
    }
    public SymbolTable getOrCreateChildTable(String name)
    {
        return childTables.computeIfAbsent(name, key -> new SymbolTable(this.name + "/" + name, this, root));
    }
    //endregion
    //region Add Symbols
    public <T extends AbstractSymbol> boolean addSymbolWithUniqueName(T symbol, boolean allowShadowing)
    {
        if (lookupByName(symbol.getClass(), symbol.name(), !allowShadowing).size() > 0) return false;
        addSymbol(symbol);
        return true;
    }
    public <T extends AbstractSymbol> void addSymbol(T symbol)
    {
        ((List<T>) localSymbols.computeIfAbsent(symbol.getClass(), SymbolTable::newSymbolList)).add(symbol);
    }
    //endregion
    //region Lookups
    public <T extends AbstractSymbol> List<Lookup<T>> lookupByName(Class<T> clazz, String name, boolean recursive)
    {
        return lookup(clazz, symbol -> symbol.name().equals(name), recursive);
    }
    public <T extends AbstractSymbol> List<Lookup<T>> lookupByType(Class<T> clazz, boolean recursive)
    {
        return lookup(clazz, symbol -> true, recursive);
    }
    
    public <T extends AbstractSymbol> List<Lookup<T>> lookup(Class<T> clazz, Predicate<AbstractSymbol> predicate, boolean recursive)
    {
        List<Lookup<T>> lookup = new ArrayList<>();
        lookupHelper(lookup, clazz, predicate, 0, this, recursive);
        return lookup;
    }
    private <T extends AbstractSymbol> void lookupHelper(List<Lookup<T>> lookup, Class<T> clazz, Predicate<AbstractSymbol> predicate, int parentLevel, SymbolTable sourceTable, boolean recursive)
    {
        List<T> symbols = (List<T>)localSymbols.get(clazz);
        if (symbols != null) for (T symbol : symbols) if (predicate.test(symbol)) lookup.add(new Lookup<>(symbol, parentLevel, sourceTable, this));
        if (recursive && parent != null) parent.lookupHelper(lookup, clazz, predicate, parentLevel + 1, sourceTable, true);
    }
    //endregion
    //region Getters
    public String name() { return name; }
    public SymbolTable parent() { return parent; }
    public SymbolTable root() { return root; }
    //endregion
    
    @Override public String toString() { return "SymbolTable[" + name + "]"; }
}
