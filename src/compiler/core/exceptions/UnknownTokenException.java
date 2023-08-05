package compiler.core.exceptions;

import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;

public class UnknownTokenException extends CompilerException
{
    public UnknownTokenException(SourceCollection source, SourcePosition position)
    {
        super(source, position);
        StringBuilder message = new StringBuilder("Unknown token '");
        
        // Build unknown token
        char c = position.getCharacter();
        while (!Character.isWhitespace(c))
        {
            message.append(c);
            position = source.advance(position);
            c = position.getCharacter();
        }
        
        message.append("'!");
        this.message = message.toString();
    }
}
