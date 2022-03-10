package mcl.compiler.exceptions;

public class MCLException extends Exception
{
    public MCLException() { super(); }
    public MCLException(String message) { super(message); }
    public MCLException(Throwable cause) { super(cause); }
    public MCLException(String message, Throwable cause) { super(message, cause); }
}
