package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;

public class MCLSemanticException extends MCLException
{
    public MCLSemanticException() { super(); }
    public MCLSemanticException(String message) { super(message); }
    public MCLSemanticException(Throwable cause) { super(cause); }
    public MCLSemanticException(String message, Throwable cause) { super(message, cause); }

    public MCLSemanticException(Token at, String message)
    {
        this(at.getLocation() + " " + message);
    }
}
