package compiler.core.parser.nodes.expression;

import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class CastOperationNode extends AbstractValueNode
{
    public final DataTypeNode castType;
    public final AbstractValueNode expression;
    
    public CastOperationNode(SourcePosition start, SourcePosition end, DataTypeNode castType, AbstractValueNode expression)
    {
        super(start, end);
        this.castType = castType;
        this.expression = expression;
    }
    
    @Override
    public Optional<Object> getValue()
    {
        return Optional.empty();
    }
    
    @Override
    public DataType getValueType()
    {
        return castType.value;
    }
    
    @Override
    protected Result<Void> validate()
    {
        if (!expression.getValueType().canExplicitCast(castType.value)) return Result.fail(new CompilerException(start(), end(), "Cannot cast " + expression.getValueType().name() + " to " + castType.value.name() + "!"));
        return Result.of(null);
    }
}
