package compiler.core.parser.nodes.expression;

import compiler.core.lexer.Token;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.types.DataType;

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
        if (leftType == rightType) return leftType;
        else return DataType.UNKNOWN;
    }
    
    @Override
    protected Result<Void> validate()
    {
        DataType leftType = left.getValueType();
        DataType rightType = right.getValueType();
        DataType resultType = getValueType();
        
        if (leftType != DataType.UNKNOWN && rightType != DataType.UNKNOWN && resultType == DataType.UNKNOWN)
        {
            return Result.fail(new CompilerException(start(), end(), "No implicit casts between '" + leftType.name() + "' and '" + rightType.name() + "'!"));
        }
        else return Result.of(null);
    }
}
