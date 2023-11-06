package compiler.core.parser.symbols.types;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.AbstractSymbol;
import compiler.core.util.types.DataType;

public abstract class AbstractTypedSymbol<T extends AbstractNode> extends AbstractSymbol<T>
{
    private DataType type;
    
    public AbstractTypedSymbol(T definition, String name, DataType type)
    {
        super(definition, name);
        this.type = type;
    }
    
    public DataType getType() { return type; }
    public void setType(DataType type) { this.type = type; }
}
