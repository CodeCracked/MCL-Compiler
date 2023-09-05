package compiler.core.parser.nodes.components;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.AbstractNode;
import compiler.core.util.types.DataType;

public class DataTypeNode extends AbstractNode
{
    public final DataType value;
    
    public DataTypeNode(Token token)
    {
        super(token.start(), token.end());
        assert token.type() == TokenType.DATA_TYPE;
        this.value = (DataType) token.contents();
    }
    
    @Override public String toString() { return value.keyword(); }
}
