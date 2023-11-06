package mcl.parser.symbols;

import compiler.core.parser.nodes.components.AbstractVariableDeclarationNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.types.AbstractVariableSymbol;
import compiler.core.util.types.DataType;

public class VariableSymbol extends AbstractVariableSymbol
{
    private final String namespace;
    
    public VariableSymbol(AbstractVariableDeclarationNode<?> definition, String namespace, String identifier, DataType type, AbstractValueNode defaultValue)
    {
        super(definition, identifier, type, defaultValue);
        this.namespace = namespace;
    }
    
    public String getNBTKey() { return "TransientCallStack[0]." + namespace + "_" + name(); }
}
