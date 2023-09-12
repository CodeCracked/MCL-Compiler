package mcl.parser.nodes.components;

import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class FunctionCallNode extends AbstractValueNode
{
    public final QualifiedIdentifierNode function;
    public final ArgumentListNode arguments;
    
    public FunctionCallNode(QualifiedIdentifierNode function, ArgumentListNode arguments)
    {
        super(function.start(), function.end());
        this.function = function;
        this.arguments = arguments;
    }
    
    @Override
    public Optional<Object> getValue()
    {
        return Optional.empty();
    }
    
    @Override
    public DataType getValueType()
    {
        // TODO: Add Symbol Lookup
        return null;
    }
}
