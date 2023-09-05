package compiler.core.parser.nodes.expression;

import compiler.core.parser.AbstractNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.types.DataType;

import java.util.Optional;

public abstract class AbstractValueNode extends AbstractNode
{
    public AbstractValueNode(SourcePosition start, SourcePosition end) { super(start, end); }
    
    public abstract Optional<Object> getValue();
    public abstract DataType getValueType();
}
