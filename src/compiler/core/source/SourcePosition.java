package compiler.core.source;

import java.util.Objects;

public class SourcePosition
{
    final CodeSource source;
    final int sourceIndex;
    final int line;
    final int column;
    
    SourcePosition(CodeSource source, int sourceIndex, int line, int column)
    {
        this.source = source;
        this.sourceIndex = sourceIndex;
        this.line = line;
        this.column = column;
    }
    
    public int getLine() { return line; }
    public int getColumn() { return column; }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourcePosition that = (SourcePosition) o;
        return line == that.line && column == that.column && source.equals(that.source);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(source, line, column);
    }
    @Override
    public String toString()
    {
        return source.toString() + ", Line " + (line + 1) + " Column " + (column + 1);
    }
}
