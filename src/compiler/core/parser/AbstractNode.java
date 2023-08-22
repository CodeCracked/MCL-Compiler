package compiler.core.parser;

import compiler.core.source.SourcePosition;
import compiler.core.util.IO;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractNode
{
    private static final String DEBUG_INDENT = "  ";
    
    private final SourcePosition start;
    private final SourcePosition end;
    private final List<Field> nodeFields;
    private final List<Field> nodeCollectionFields;
    
    AbstractNode parent;
    
    public AbstractNode(SourcePosition start, SourcePosition end)
    {
        this.start = start;
        this.end = end;
        this.nodeFields = new ArrayList<>();
        this.nodeCollectionFields = new ArrayList<>();
        populateFieldLists();
    }
    
    public void decorate() { forEachChild(child -> child.parent = this, true); }
    
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
            }
            
            // Print node collection fields
            for (Field field : nodeCollectionFields)
            {
                field.setAccessible(true);
                Collection<AbstractNode> nodes = (Collection <AbstractNode>) field.get(this);
                if (nodes.size() > 0)
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
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        try
        {
            // Accept Nodes
            for (Field field : nodeFields)
            {
                AbstractNode node = (AbstractNode) field.get(this);
                consumer.accept(node);
            }
            for (Field field : nodeCollectionFields)
            {
                Collection<AbstractNode> nodeCollection = (Collection<AbstractNode>) field.get(this);
                for (AbstractNode node : nodeCollection) consumer.accept(node);
            }
            
            // Recursion
            if (recursive)
            {
                for (Field field : nodeFields)
                {
                    AbstractNode node = (AbstractNode) field.get(this);
                    node.forEachChild(consumer, true);
                }
                for (Field field : nodeCollectionFields)
                {
                    Collection<AbstractNode> nodeCollection = (Collection<AbstractNode>) field.get(this);
                    for (AbstractNode node : nodeCollection) node.forEachChild(consumer, true);
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    //endregion
    //region Getters
    public AbstractNode parent() { return parent; }
    public SourcePosition start() { return start; }
    public SourcePosition end() { return end; }
    //endregion
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }
}
