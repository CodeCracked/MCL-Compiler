package compiler.core.util.exceptions;

import compiler.core.source.SourcePosition;

public class CompilerException extends Exception
{
    protected final SourcePosition start;
    protected final SourcePosition end;
    protected final String message;
    
    protected CompilerException(SourcePosition start, SourcePosition end)
    {
        this.start = start;
        this.end = end;
        this.message = "";
    }
    public CompilerException(SourcePosition start, SourcePosition end, String message)
    {
        this.start = start;
        this.end = end;
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return start + ": " + message;
    }
}
