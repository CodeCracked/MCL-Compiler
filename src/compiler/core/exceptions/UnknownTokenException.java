package compiler.core.exceptions;

import compiler.core.source.SourcePosition;

public class UnknownTokenException extends CompilerException
{
    public UnknownTokenException(SourcePosition position)
    {
        super(position.copy());
        
        if (position.valid())
        {
            StringBuilder message = new StringBuilder("Unknown token '");
    
            // Build unknown token
            boolean matchingWhitespace = Character.isWhitespace(position.getCharacter());
            while (position.valid() && Character.isWhitespace(position.getCharacter()) == matchingWhitespace)
            {
                message.append(position.getCharacter());
                position.advance();
            }
    
            message.append("'!");
            this.message = message.toString();
        }
        else this.message = "Unexpected end-of-file!";
    }
}
