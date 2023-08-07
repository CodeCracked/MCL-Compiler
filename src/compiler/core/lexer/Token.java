package compiler.core.lexer;

import compiler.core.source.SourcePosition;

import java.util.Objects;

public class Token
{
    protected final Enum<?> type;
    protected final Object contents;
    protected final SourcePosition start;
    protected final SourcePosition end;
    
    public Token(Enum<?> type, Object contents, SourcePosition start, SourcePosition end)
    {
        this.type = type;
        this.contents = contents;
        this.start = start;
        this.end = end;
    }
    
    public final Enum<?> type() { return type; }
    public final Object contents() { return contents; }
    public final SourcePosition start() { return start; }
    public final SourcePosition end() { return end; }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
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
        return contents != null ? type.toString() + "[" + contents + "]" : type.toString();
    }
}
