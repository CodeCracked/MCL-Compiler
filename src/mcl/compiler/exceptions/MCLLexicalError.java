package mcl.compiler.exceptions;

import mcl.compiler.source.CodeLocation;

public class MCLLexicalError extends MCLError
{
    public MCLLexicalError(CodeLocation start, CodeLocation end, String error, String details)
    {
        super(start, end, error, details);
    }
}