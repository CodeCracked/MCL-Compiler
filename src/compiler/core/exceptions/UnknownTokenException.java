package compiler.core.exceptions;

import compiler.core.source.SourcePosition;

public class UnknownTokenException extends CompilerException
{
    public UnknownTokenException(SourcePosition position)
    {
        super(position);
        StringBuilder message = new StringBuilder("Unknown token '");
        
        // Build unknown token
        while (position.valid())
        {
            message.append(position.getCharacter());
            if (Character.isWhitespace(position.getCharacter()) || !position.advance()) break;
        }
        
        message.append("'!");
        this.message = message.toString();
    }
}
