package compiler.core.parser.nodes.expression;

import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class LiteralNode extends AbstractValueNode
{
    public final DataType type;
    public final Object value;
    
    public LiteralNode(SourcePosition start, SourcePosition end, DataType type, Object value)
    {
        super(start, end);
        this.type = type;
        this.value = value;
    }
    
    @Override public Optional<Object> getValue() { return Optional.of(value); }
    @Override public DataType getValueType() { return type; }
    
    @Override
    public Result<AbstractValueNode> cast(SourcePosition dataTypeStart, SourcePosition dataTypeEnd, SourcePosition start, SourcePosition end, DataType castTo)
    {
        DataType.CastFunction castFunction = type.getCastFunction(castTo);
        if (castFunction == null) return Result.fail(new CompilerException(start, end, "Cannot cast literal of type " + type.name() + " to " + castTo.name() + "!"));
        else return Result.of(new LiteralNode(dataTypeStart, end, castTo, castFunction.cast(value)));
    }
    
    @Override public String toString() { return "Literal[(" + type.name() + ")" + value + "]"; }
}
