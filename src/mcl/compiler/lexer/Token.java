package mcl.compiler.lexer;

public record Token(TokenType type, Object value, int startPosition, int endPosition)
{
    public Token(TokenType type, int startPosition, int endPosition)
    {
        this(type, null, startPosition, endPosition);
    }

    public boolean matches(TokenType tokenType, Object value)
    {
        return this.type == tokenType && this.value.equals(value);
    }

    @Override
    public String toString()
    {
        if (value != null) return type.name() + ":" + value;
        else return type.name();
    }
}
