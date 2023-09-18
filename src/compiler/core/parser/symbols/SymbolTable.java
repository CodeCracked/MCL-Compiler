package compiler.core.parser.symbols;

import compiler.core.parser.AbstractNode;
import compiler.core.util.IO;
import compiler.core.util.Ref;
import compiler.core.util.Result;
import compiler.core.util.exceptions.DuplicateSymbolException;
import compiler.core.util.exceptions.MultipleSymbolsFoundException;
import compiler.core.util.exceptions.UndefinedSymbolException;

import javax.swing.plaf.synth.SynthUI;
import java.util.*;
import java.util.function.Predicate;

public class SymbolTable
{
    public static final class Lookup<T extends AbstractSymbol>
    {
        private final AbstractNode source;
        private final Class<T> symbolClass;
        private final String symbolType;
        private final String errorDescription;
        private final List<SymbolEntry<T>> symbols;
        
        private Lookup(AbstractNode source, Class<T> symbolClass, String errorDescription)
        {
            this.source = source;
            this.symbolClass = symbolClass;
            this.symbolType = symbolClass.getSimpleName().replace("Symbol", "").toLowerCase().trim();
            this.errorDescription = errorDescription;
            this.symbols = new ArrayList<>();
        }
        private Lookup(Lookup<T> existing)
        {
            this(existing.source, existing.symbolClass, existing.errorDescription);
            this.symbols.addAll(existing.symbols);
        }
        
        public int count() { return symbols.size(); }
        public Class<T> symbolClass() { return symbolClass; }
        
        //region Modification
        public Lookup<T> filter(Predicate<SymbolEntry<T>> filter)
        {
            Lookup<T> filtered = new Lookup<>(this);
            return filtered.filterSelf(filter);
        }
        public Lookup<T> filterSelf(Predicate<SymbolEntry<T>> filter)
        {
            List<SymbolEntry<T>> removeList = new ArrayList<>();
            for (SymbolEntry<T> test : symbols) if (!filter.test(test)) removeList.add(test);
            symbols.removeAll(removeList);
            return this;
        }
        //endregion
        //region Resolutions
        public Result<SymbolEntry<T>> single()
        {
            Lookup<T> filtered = filter(symbol -> symbol.symbol.canBeReferencedBy(source));
            if (filtered.symbols.size() == 0) return Result.fail(new UndefinedSymbolException(source, symbolType, errorDescription));
            else if (filtered.symbols.size() > 1) return Result.fail(new MultipleSymbolsFoundException(source, symbolType));
            else return Result.of(filtered.symbols.get(0));
        }
        public Result<Optional<SymbolEntry<T>>> optional()
        {
            Lookup<T> filtered = filter(symbol -> symbol.symbol.canBeReferencedBy(source));
            if (filtered.symbols.size() > 1) return Result.fail(new MultipleSymbolsFoundException(source, symbolType));
            else if (filtered.symbols.size() == 0) return Result.of(Optional.empty());
            else return Result.of(Optional.of(filtered.symbols.get(0)));
        }
        public Result<List<SymbolEntry<T>>> oneOrMore()
        {
            Lookup<T> filtered = filter(symbol -> symbol.symbol.canBeReferencedBy(source));
            if (filtered.symbols.size() == 0) return Result.fail(new UndefinedSymbolException(source, symbolType, errorDescription));
            else return Result.of(Collections.unmodifiableList(filtered.symbols));
        }
        public Result<List<SymbolEntry<T>>> any()
        {
            Lookup<T> filtered = filter(symbol -> symbol.symbol.canBeReferencedBy(source));
            return Result.of(Collections.unmodifiableList(filtered.symbols));
        }
        //endregion
    }
    public static final class SymbolEntry<T extends AbstractSymbol>
    {
        private final T symbol;
        private final int parentLevel;
        private final SymbolTable lookupTable;
        private final SymbolTable ownerTable;
        
        private SymbolEntry(T symbol, int parentLevel, SymbolTable lookupTable, SymbolTable ownerTable)
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
    public Optional<SymbolTable> tryGetChildTable(String name)
    {
        return Optional.ofNullable(childTables.get(name));
    }
    //endregion
    //region Add Symbols
    public <T extends AbstractSymbol> boolean addSymbolWithUniqueName(AbstractNode source, T symbol, boolean allowShadowing)
    {
        // Check for existing symbols
        Lookup<T> existingSymbols = (Lookup<T>) lookupByName(source, symbol.getClass(), symbol.name());
        if (allowShadowing) existingSymbols.filterSelf(entry -> entry.parentLevel == 0);
        if (existingSymbols.count() > 0) return false;
        
        // Add symbol
        addSymbol(symbol);
        return true;
    }
    public <T extends AbstractSymbol> void addSymbol(T symbol)
    {
        ((List<T>) localSymbols.computeIfAbsent(symbol.getClass(), SymbolTable::newSymbolList)).add(symbol);
    }
    //endregion
    //region Lookups
    public <T extends AbstractSymbol> Lookup<T> lookupByName(AbstractNode source, Class<T> clazz, String name)
    {
        return lookup(source, clazz, symbol -> symbol.name().equals(name), " '" + name + "'");
    }
    public <T extends AbstractSymbol> Lookup<T> lookupByType(AbstractNode source, Class<T> clazz)
    {
        return lookup(source, clazz, symbol -> true, null);
    }
    public <T extends AbstractSymbol> Lookup<T> lookup(AbstractNode source, Class<T> clazz, Predicate<AbstractSymbol> predicate, String errorDescription)
    {
        Lookup<T> lookup = new Lookup<>(source, clazz, errorDescription);
        lookupHelper(lookup, clazz, predicate, 0, this);
        return lookup;
    }
    
    private <T extends AbstractSymbol> void lookupHelper(Lookup<T> lookup, Class<T> clazz, Predicate<AbstractSymbol> predicate, int parentLevel, SymbolTable sourceTable)
    {
        List<T> symbols = (List<T>)localSymbols.get(clazz);
        if (symbols != null) for (T symbol : symbols) if (predicate.test(symbol)) lookup.symbols.add(new SymbolEntry<>(symbol, parentLevel, sourceTable, this));
        if (parent != null) parent.lookupHelper(lookup, clazz, predicate, parentLevel + 1, sourceTable);
    }
    //endregion
    //region Getters
    public String name() { return name; }
    public SymbolTable parent() { return parent; }
    public SymbolTable root() { return root; }
    //endregion
    
    @Override public String toString() { return "SymbolTable[" + name + "]"; }
}
