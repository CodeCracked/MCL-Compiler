package compiler.core.parser.nodes.expression;

import compiler.core.lexer.Token;
import compiler.core.types.DataType;

import java.util.Optional;

public class BinaryOperationNode extends AbstractValueNode
{
    public final AbstractValueNode left;
    public final Token operation;
    public final AbstractValueNode right;
    
    public BinaryOperationNode(AbstractValueNode left, Token operation, AbstractValueNode right)
    {
        super(left.start(), right.end());
        this.left = left;
        this.operation = operation;
        this.right = right;
    }
    
    @Override
    public String toString()
    {
        return "BinaryOperationNode[" + operation.type().name() + "]";
    }
    
    @Override
    public Optional<Object> getValue()
    {
        // TODO: Implement this
        return Optional.empty();
    }
    
    @Override
    public DataType getValueType()
    {
        DataType leftType = left.getValueType();
        DataType rightType = right.getValueType();
        return leftType.resultWith(rightType);
    }
}
