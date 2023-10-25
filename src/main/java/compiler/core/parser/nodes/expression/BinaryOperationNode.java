package compiler.core.parser.nodes.expression;

import compiler.core.lexer.Token;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class BinaryOperationNode extends AbstractValueNode
{
    public AbstractValueNode left;
    public final Token operation;
    public AbstractValueNode right;
    
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
        Result<Void> result = new Result<>();
        DataType leftType = left.getValueType();
        DataType rightType = right.getValueType();
        
        if (leftType == DataType.UNKNOWN) return result.failure(new CompilerException(left.start(), left.end(), "Left argument has an unknown data type!"));
        if (rightType == DataType.UNKNOWN) return result.failure(new CompilerException(right.start(), right.end(), "Right argument has an unknown data type!"));
        
        if (leftType != rightType)
        {
            if (rightType.canImplicitCast(leftType))
            {
                AbstractValueNode casted = result.register(right.cast(right.start(), right.start(), right.start(), right.end(), leftType));
                if (result.getFailure() != null) return result;
                
                right = casted;
                return result.success(null);
            }
            else if (leftType.canImplicitCast(rightType))
            {
                AbstractValueNode casted = result.register(left.cast(left.start(), left.start(), left.start(), left.end(), rightType));
                if (result.getFailure() != null) return result;
                
                left = casted;
                return result.success(null);
            }
            else return result.failure(new CompilerException(start(), end(), "No implicit casts between '" + leftType.name() + "' and '" + rightType.name() + "'!"));
        }
        else return result.success(null);
    }
}
