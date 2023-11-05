package mcl.parser.symbols;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.types.DataType;

public class MCLVariableSymbol extends VariableSymbol
{
    private final String namespace;
    
    public MCLVariableSymbol(AbstractNode definition, String namespace, String identifier, DataType type, AbstractValueNode defaultValue)
    {
        super(definition, identifier, type, defaultValue);
        this.namespace = namespace;
    }
    
    public String getNBTKey() { return "TransientCallStack[0]." + namespace + "_" + name(); }
}
