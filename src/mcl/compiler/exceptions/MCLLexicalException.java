package mcl.compiler.exceptions;

public class MCLLexicalException extends MCLException
{
    public MCLLexicalException(String token)
    {
        super("Unknown MCL Token: " + token);
    }
}
