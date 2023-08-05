package compiler.core.exceptions;

import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;

public class CompilerException extends Exception
{
    protected final SourceCollection source;
    protected final SourcePosition position;
    protected String message;
    
    protected CompilerException(SourceCollection source, SourcePosition position)
    {
        this.source = source;
        this.position = position;
        this.message = "";
    }
    public CompilerException(SourceCollection source, SourcePosition position, String message)
    {
        this.source = source;
        this.position = position;
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return position + ": " + message;
    }
}
