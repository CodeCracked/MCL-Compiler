package compiler.core.parser.nodes.expression;

import compiler.core.lexer.Token;
import compiler.core.source.SourcePosition;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class UnaryOperationNode extends AbstractValueNode
{
    public final Token operation;
    public final AbstractValueNode expression;
    
    public UnaryOperationNode(SourcePosition start, SourcePosition end, Token operation, AbstractValueNode expression)
    {
        super(start, end);
        this.operation = operation;
        this.expression = expression;
    }
    
    @Override
    public String toString()
    {
        return "UnaryOperationNode[" + operation.type().name() + "]";
    }
    
    @Override
    public Optional<Object> getValue()
    {
        return Optional.empty();
    }
    
    @Override
    public DataType getValueType()
    {
        return expression.getValueType();
    }
}
