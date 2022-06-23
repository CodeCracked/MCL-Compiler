package mcl.compiler.exceptions;

import mcl.compiler.source.CodeLocation;

public class MCLLexicalError extends MCLError
{
    public MCLLexicalError(CodeLocation start, CodeLocation end, String details)
    {
        super(start, end, "Unknown Token", details);
    }
}