package compiler.core.source;

import compiler.core.util.IO;

import java.util.Objects;
import java.util.Stack;

@SuppressWarnings("UnusedReturnValue")
public class SourcePosition
{
    final CodeSource source;
    int line;
    int column;
    char character;
    
    private final Stack<SourcePosition> revertStack;
    
    private SourcePosition(SourcePosition other)
    {
        this.source = other.source;
        this.line = other.line;
        this.column = other.column;
        this.character = other.character;
        
        this.revertStack = new Stack<>();
        for (SourcePosition revert : other.revertStack) this.revertStack.push(revert);
    }
    SourcePosition(CodeSource source, int line, int column)
    {
        this.source = source;
        this.line = line;
        this.column = column;
        this.character = source.charAt(this);
        this.revertStack = new Stack<>();
    }
    
    public SourcePosition copy()
    {
        return new SourcePosition(this);
    }
    public void moveTo(SourcePosition position)
    {
        assert position.source.equals(source);
        this.line = position.line;
        this.column = position.column;
        this.character = position.character;
    }
    
    //region Getters
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public char getCharacter() { return character; }
    public CodeSource getSource() { return source; }
    //endregion
    //region Position Changing
    public boolean advance()
    {
        if (!source.advance(this)) return false;
        character = source.charAt(this);
        return true;
    }
    public boolean retract()
    {
        if (!source.retract(this)) return false;
        character = source.charAt(this);
        return true;
    }
    public boolean valid()
    {
        return source.valid(this);
    }
    //endregion
    //region Position Marking
    public void markPosition() { this.revertStack.push(new SourcePosition(source, line, column)); }
    public void unmarkPosition()
    {
        if (revertStack.size() == 0)
        {
            String error = "Trying to unmark position of SourcePosition when no positions have been marked!";
            IO.Errors.println(error);
            throw new IllegalStateException(error);
        }
        else this.revertStack.pop();
    }
    public void revertPosition()
    {
        if (revertStack.size() == 0)
        {
            String error = "Trying to revert position of SourcePosition when no positions have been marked!";
            IO.Errors.println(error);
            throw new IllegalStateException(error);
        }
        else moveTo(revertStack.pop());
    }
    //endregion
    //region Comparison
    public boolean isInSameSource(SourcePosition other)
    {
        return source.equals(other.source);
    }
    public boolean isBefore(SourcePosition other)
    {
        if (!source.equals(other.source)) return true;
        else if (line < other.line) return true;
        else return line == other.line && column < other.column;
    }
    public boolean isAfter(SourcePosition other)
    {
        if (!source.equals(other.source)) return true;
        else if (line > other.line) return true;
        else return line == other.line && column > other.column;
    }
    //endregion
    //region Object Overrides
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
    //endregion
}
