package compiler.core.parser.nodes.expression;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;

import java.util.Optional;

public abstract class AbstractValueNode extends AbstractNode
{
    public AbstractValueNode(SourcePosition start, SourcePosition end) { super(start, end); }
    
    public abstract Optional<Object> getValue();
    public abstract DataType getValueType();
    
    public Result<AbstractValueNode> cast(DataTypeNode castTo) { return cast(castTo.start(), castTo.end(), castTo.start(), end(), castTo.value); }
    public Result<AbstractValueNode> cast(SourcePosition dataTypeStart, SourcePosition dataTypeEnd, SourcePosition start, SourcePosition end, DataType castTo)
    {
        return Result.of(new CastOperationNode(start, end, new DataTypeNode(new Token(TokenType.DATA_TYPE, castTo, dataTypeStart, dataTypeEnd)), this));
    }
}
