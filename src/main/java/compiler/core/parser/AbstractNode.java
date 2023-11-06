package compiler.core.parser;

import compiler.core.parser.symbols.SymbolTable;
import compiler.core.source.SourcePosition;
import compiler.core.util.IO;
import compiler.core.util.Result;
import compiler.core.util.annotations.OptionalChild;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class AbstractNode
{
    private static final String DEBUG_INDENT = "  ";
    
    private final SourcePosition start;
    private final SourcePosition end;
    private final boolean hasCodeGen;
    private final List<Field> nodeFields;
    private final List<Field> nodeCollectionFields;
    
    AbstractNode parent;
    private SymbolTable symbolTable;
    
    public AbstractNode(SourcePosition start, SourcePosition end) { this(start, end, true); }
    public AbstractNode(SourcePosition start, SourcePosition end, boolean hasCodeGen)
    {
        this.start = start;
        this.end = end;
        this.hasCodeGen = hasCodeGen;
        this.nodeFields = new ArrayList<>();
        this.nodeCollectionFields = new ArrayList<>();
        populateFieldLists();
    }
    
    //region Symbol Tables
    protected SymbolTable getChildSymbolTable(AbstractNode child) { return symbolTable; }
    protected void setSymbolTable(SymbolTable table) { this.symbolTable = table; }
    //endregion
    //region Decoration
    final Result<Void> linkHierarchy() { return forEachChild((parent, child) -> child.parent = parent, true); }
    protected Result<Void> populateMetadata() { return Result.of(null); }
    final Result<Void> assignSymbolTables() { return forEachChild((parent, child) -> child.setSymbolTable(parent.getChildSymbolTable(child)), true); }
    protected Result<Void> createSymbols() { return Result.of(null); }
    protected Result<Void> retrieveSymbols() { return Result.of(null); }
    protected Result<Void> validate() { return Result.of(null); }
    //endregion
    //region Public Helpers
    public <T> Result<T> findParentNode(Class<T> clazz)
    {
        AbstractNode node = parent;
        while (node != null)
        {
            if (node.getClass() == clazz) break;
            else node = node.parent;
        }
        
        if (node == null) return Result.fail(new IllegalStateException("Node does not contain an ancestor of type " + clazz.getSimpleName() + "!"));
        else return Result.of((T)node);
    }
    public final String getMCLDescription()
    {
        return start.toString() + ": " + getMCLCode().split("\n")[0].trim();
    }
    public final String getMCLCode()
    {
        if (!start.isInSameSource(end)) return "<Node spans multiple sources>";
        
        SourcePosition current = start.copy();
        StringBuilder builder = new StringBuilder();
        while (current.isBefore(end) || current.equals(end))
        {
            builder.append(current.getCharacter());
            if (!current.advance()) break;
        }
        return builder.toString();
    }
    //endregion
    //region Reflection
    private void populateFieldLists()
    {
        // Use reflection to get fields
        Class<?> currentClass = getClass();
        while (!currentClass.equals(AbstractNode.class))
        {
            // Process each field in the class
            for (Field field : currentClass.getFields())
            {
                // If field is a single node
                if (AbstractNode.class.isAssignableFrom(field.getType())) nodeFields.add(field);
                
                // If field is a collection
                else if (Collection.class.isAssignableFrom(field.getType()))
                {
                    // Ensure field is a generic collection
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType genericType)
                    {
                        // If field is a collection of nodes
                        if (genericType.getActualTypeArguments()[0] instanceof Class<?> genericClass && AbstractNode.class.isAssignableFrom(genericClass))
                        {
                            nodeCollectionFields.add(field);
                        }
                    }
                }
            }
        
            // Process superclass
            currentClass = currentClass.getSuperclass();
        }
        
        // Set all fields to accessible
        for (Field field : nodeFields) field.setAccessible(true);
        for (Field field : nodeCollectionFields) field.setAccessible(true);
    }
    //endregion
    //region Debug Printing
    public void debugPrint(int depth)
    {
        IO.Debug.println(this);
        printDebugContents(depth + 1);
    }
    protected void printDebugContents(int depth)
    {
        // Use reflection to print field debug info
        try
        {
            // Print single node fields
            for (Field field : nodeFields)
            {
                field.setAccessible(true);
                Object value = field.get(this);
                
                if (value instanceof AbstractNode node) printChildDebug(depth, field.getName(), node);
                else if (value == null)
                {
                    OptionalChild annotation = field.getAnnotation(OptionalChild.class);
                    if (annotation != null && annotation.alwaysShow()) IO.Debug.println(DEBUG_INDENT.repeat(depth) + field.getName() + ": null");
                }
            }
            
            // Print node collection fields
            for (Field field : nodeCollectionFields)
            {
                field.setAccessible(true);
                Collection<AbstractNode> nodes = (Collection <AbstractNode>) field.get(this);
                if (nodes == null)
                {
                    OptionalChild annotation = field.getAnnotation(OptionalChild.class);
                    if (annotation != null && annotation.alwaysShow()) IO.Debug.println(DEBUG_INDENT.repeat(depth) + field.getName() + ": null");
                }
                else if (nodes.size() > 0)
                {
                    IO.Debug.println(DEBUG_INDENT.repeat(depth) + field.getName() + ":");
                    IO.Debug.println(DEBUG_INDENT.repeat(depth) + "[");
                    
                    int remaining = nodes.size();
                    for (AbstractNode node : nodes)
                    {
                        printChildDebug(depth + 1, null, node);
                        remaining--;
                        if (remaining > 0) IO.Debug.println();
                    }
                    
                    IO.Debug.println(DEBUG_INDENT.repeat(depth) + "]");
                }
                else IO.Debug.println(DEBUG_INDENT.repeat(depth) + field.getName() + ": []");
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }
    protected void printChildDebug(int depth, String name, AbstractNode node)
    {
        IO.Debug.print(DEBUG_INDENT.repeat(depth));
        if (name != null) IO.Debug.print(name + ": ");
        node.debugPrint(depth);
    }
    //endregion
    //region Iteration
    public Result<Void> forEachChild(BiConsumer<AbstractNode, AbstractNode> consumer, boolean recursive) { return forEachChild(consumer, recursive, false); }
    public Result<Void> forEachChildWithResult(BiFunction<AbstractNode, AbstractNode, Result<?>> consumer, boolean recursive) { return forEachChildWithResult(consumer, recursive, false); }
    public Result<Void> forEachChild(BiConsumer<AbstractNode, AbstractNode> consumer, boolean recursive, boolean bottomUp)
    {
        return forEachChildWithResult((parent, child) -> { consumer.accept(parent, child); return Result.of((Void)null); }, recursive, bottomUp);
    }
    public Result<Void> forEachChildWithResult(BiFunction<AbstractNode, AbstractNode, Result<?>> consumer, boolean recursive, boolean bottomUp)
    {
        Result<Void> result = new Result<>();
        
        try
        {
            if (recursive && bottomUp)
            {
                result.register(forEachRecursion(consumer, true));
                if (result.getFailure() != null) return result;
            }
            
            result.register(forEach(consumer));
            if (result.getFailure() != null) return result;
            
            if (recursive && !bottomUp)
            {
                result.register(forEachRecursion(consumer, false));
                if (result.getFailure() != null) return result;
            }
        }
        catch (Exception e) { return result.failure(e); }
        
        return result.success(null);
    }
    
    private Result<Void> forEach(BiFunction<AbstractNode, AbstractNode, Result<?>> consumer) throws IllegalAccessException
    {
        Result<Void> result = new Result<>();
        
        // Accept Nodes
        for (Field field : nodeFields)
        {
            AbstractNode node = (AbstractNode) field.get(this);
            if (node != null)
            {
                result.register(consumer.apply(this, node));
                if (result.getFailure() != null) return result;
            }
            else
            {
                OptionalChild annotation = field.getAnnotation(OptionalChild.class);
                if (annotation == null) result.addError(new IllegalStateException(getClass().getSimpleName() + "'s non-optional child '" + field.getName() + "' is null!"));
                else if (annotation.warn()) result.addWarning(new IllegalStateException(getClass().getSimpleName() + "'s optional child '" + field.getName() + "' is null."));
            }
        }
        for (Field field : nodeCollectionFields)
        {
            Collection<AbstractNode> nodeCollection = (Collection<AbstractNode>) field.get(this);
            if (nodeCollection != null)
            {
                for (AbstractNode node : nodeCollection)
                {
                    result.register(consumer.apply(this, node));
                    if (result.getFailure() != null) return result;
                }
            }
            else
            {
            
                OptionalChild annotation = field.getAnnotation(OptionalChild.class);
                if (annotation == null) result.addError(new IllegalStateException(getClass().getSimpleName() + "'s non-optional child '" + field.getName() + "' is null!"));
                else if (annotation.warn()) result.addWarning(new IllegalStateException(getClass().getSimpleName() + "'s optional child '" + field.getName() + "' is null."));
            }
        }
        
        return result.success(null);
    }
    private Result<Void> forEachRecursion(BiFunction<AbstractNode, AbstractNode, Result<?>> consumer, boolean bottomUp) throws IllegalAccessException
    {
        Result<Void> result = new Result<>();
        
        for (Field field : nodeFields)
        {
            AbstractNode node = (AbstractNode) field.get(this);
            if (node != null) result.register(node.forEachChildWithResult(consumer, true, bottomUp));
        }
        for (Field field : nodeCollectionFields)
        {
            Collection<AbstractNode> nodeCollection = (Collection<AbstractNode>) field.get(this);
            if (nodeCollection != null) for (AbstractNode node : nodeCollection) result.register(node.forEachChildWithResult(consumer, true, bottomUp));
        }
        
        return result.success(null);
    }
    //endregion
    //region Getters
    public AbstractNode parent() { return parent; }
    public SourcePosition start() { return start; }
    public SourcePosition end() { return end; }
    public boolean hasCodeGen() { return hasCodeGen; }
    public SymbolTable symbolTable() { return symbolTable; }
    //endregion
    
    @Override
    public String toString()
    {
        if (parent == null || !parent.symbolTable.equals(symbolTable)) return getClass().getSimpleName() + " <" + symbolTable.name() + ">";
        else return getClass().getSimpleName();
    }
}
