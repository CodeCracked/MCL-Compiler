package compiler.core.lexer;

import compiler.core.source.SourcePosition;

import java.util.Objects;

public record Token<T>(T type, String contents, SourcePosition start, SourcePosition end)
{
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token<?> token = (Token<?>) o;
        return type.equals(token.type) && contents.equals(token.contents) && start.equals(token.start) && end.equals(token.end);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(type, contents, start, end);
    }
    @Override
    public String toString()
    {
        return contents.length() > 0 ? type.toString() + "[" + contents + "]" : type.toString();
    }
}
