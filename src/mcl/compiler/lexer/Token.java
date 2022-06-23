package mcl.compiler.lexer;

public record Token<T>(TokenType type, T value, int startPosition, int endPosition)
{
    public Token(TokenType type, int startPosition, int endPosition)
    {
        this(type, null, startPosition, endPosition);
    }

    @Override
    public String toString()
    {
        if (value != null) return type.name() + ":" + value;
        else return type.name();
    }
}
