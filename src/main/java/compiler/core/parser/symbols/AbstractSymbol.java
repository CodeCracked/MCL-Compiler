package compiler.core.parser.symbols;

import compiler.core.parser.AbstractNode;

public abstract class AbstractSymbol<T extends AbstractNode>
{
    private final T definition;
    private final String name;
    
    public AbstractSymbol(T definition, String name)
    {
        this.definition = definition;
        this.name = name;
    }
    
    protected boolean canBeReferencedBy(AbstractNode caller) { return true; }
    
    public final T definition() { return definition; }
    public final String name() { return name; }
}
