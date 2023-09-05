package mcl.lexer;

import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.util.types.DataType;

public final class MCLDataTypes
{
    public static final DataType INTEGER = new DataType("int", LiteralTokenType.INTEGER);
    public static final DataType FLOAT = new DataType("float", LiteralTokenType.DECIMAL);
    public static final DataType STRING = new DataType("string", LiteralTokenType.STRING);
}
