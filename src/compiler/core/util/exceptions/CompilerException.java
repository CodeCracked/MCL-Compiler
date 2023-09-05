package compiler.core.util.exceptions;

import compiler.core.source.SourcePosition;

public class CompilerException extends Exception
{
    protected final SourcePosition position;
    protected final String message;
    
    protected CompilerException(SourcePosition position)
    {
        this.position = position;
        this.message = "";
    }
    public CompilerException(SourcePosition position, String message)
    {
        this.position = position;
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return position + ": " + message;
    }
}
