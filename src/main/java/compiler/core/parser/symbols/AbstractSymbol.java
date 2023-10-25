package compiler.core.parser.symbols;

import compiler.core.parser.AbstractNode;

public abstract class AbstractSymbol
{
    private final AbstractNode definition;
    private final String name;
    
    public AbstractSymbol(AbstractNode definition, String name)
    {
        this.definition = definition;
        this.name = name;
    }
    
    protected boolean canBeReferencedBy(AbstractNode caller) { return true; }
    
    public final AbstractNode definition() { return definition; }
    public final String name() { return name; }
}
