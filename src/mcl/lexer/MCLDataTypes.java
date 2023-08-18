package mcl.lexer;

import compiler.core.lexer.types.DataType;

public final class MCLDataTypes
{
    public static final DataType INTEGER = new DataType(MCLKeyword.INT);
    public static final DataType FLOAT = new DataType(MCLKeyword.FLOAT);
    public static final DataType STRING = new DataType(MCLKeyword.STRING);
}
