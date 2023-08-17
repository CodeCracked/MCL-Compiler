package compiler.core.source;

import compiler.core.util.IO;

import java.util.Objects;
import java.util.Stack;

@SuppressWarnings("UnusedReturnValue")
public class SourcePosition
{
    SourceCollection source;
    int sourceIndex;
    int line;
    int column;
    char character;
    
    private final Stack<SourcePosition> revertStack;
    
    private SourcePosition(SourcePosition other)
    {
        this.source = other.source;
        this.sourceIndex = other.sourceIndex;
        this.line = other.line;
        this.column = other.column;
        this.character = other.character;
        
        this.revertStack = new Stack<>();
        for (SourcePosition revert : other.revertStack) this.revertStack.push(revert);
    }
    SourcePosition(SourceCollection source, int sourceIndex, int line, int column)
    {
        this.source = source;
        this.sourceIndex = sourceIndex;
        this.line = line;
        this.column = column;
        this.character = source.sources[sourceIndex].charAt(this);
        this.revertStack = new Stack<>();
    }
    
    public SourcePosition copy()
    {
        return new SourcePosition(this);
    }
    public void moveTo(SourcePosition position)
    {
        this.source = position.source;
        this.sourceIndex = position.sourceIndex;
        this.line = position.line;
        this.column = position.column;
        this.character = position.character;
    }
    
    //region Getters
    public SourceCollection getSource() { return source; }
    public int getSourceIndex() { return sourceIndex; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public char getCharacter() { return character; }
    //endregion
    //region Position Changing
    public boolean advance()
    {
        if (sourceIndex < 0)
        {
            source.sources[0].moveToStart(this);
            return true;
        }
        else if (sourceIndex >= source.sources.length) return false;
        
        if (!source.sources[sourceIndex].advance(this))
        {
            sourceIndex++;
            if (sourceIndex >= source.sources.length)
            {
                line = 0;
                column = 0;
                character = '!';
                return false;
            }
            else source.sources[sourceIndex].moveToStart(this);
        }
    
        character = source.sources[sourceIndex].charAt(this);
        return true;
    }
    public boolean retract()
    {
        if (sourceIndex >= source.sources.length)
        {
            source.sources[source.sources.length - 1].moveToEnd(this);
            return true;
        }
        else if (sourceIndex < 0) return false;
        
        if (!source.sources[sourceIndex].retract(this))
        {
            sourceIndex--;
            if (sourceIndex < 0)
            {
                line = 0;
                column = 0;
                character = '!';
                return false;
            }
            else source.sources[sourceIndex].moveToEnd(this);
        }
    
        character = source.sources[sourceIndex].charAt(this);
        return true;
    }
    public boolean valid()
    {
        return sourceIndex >= 0 && sourceIndex < source.sources.length;
    }
    //endregion
    //region Position Marking
    public void markPosition() { this.revertStack.push(new SourcePosition(source, sourceIndex, line, column)); }
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
        return source.sources[sourceIndex].toString() + ", Line " + (line + 1) + " Column " + (column + 1);
    }
    //endregion
}
