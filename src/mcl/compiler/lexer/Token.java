package mcl.compiler.lexer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record Token(TokenType type, Object value, int startPosition, int endPosition)
{
    public Token(TokenType type, int startPosition, int endPosition)
    {
        this(type, null, startPosition, endPosition);
    }

    public static Token description(TokenType type, Object value) { return new Token(type, value, 0, 0); }
    public static Set<Token> descriptions(TokenType... types)
    {
        Set<Token> descriptions = new HashSet<>();
        for (TokenType type : types) descriptions.add(new Token(type, null, 0, 0));
        return Collections.unmodifiableSet(descriptions);
    }

    public boolean matches(Token description)
    {
        if (this.type != description.type) return false;
        else if (this.value == null) return description.value == null;
        else return this.value.equals(description.value);
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
