package mcl.compiler.lexer;

public record Token<T>(TokenType type, T value)
{
    public Token(TokenType type)
    {
        this(type, null);
    }

    @Override
    public String toString()
    {
        if (value != null) return type.name() + "(" + value + ")";
        else return type.name();
    }
}
