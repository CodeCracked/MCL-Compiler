package compiler.core.parser.symbols;

public abstract class AbstractSymbol
{
    private final String name;
    
    public AbstractSymbol(String name)
    {
        this.name = name;
    }
    
    public String name() { return name; }
}
