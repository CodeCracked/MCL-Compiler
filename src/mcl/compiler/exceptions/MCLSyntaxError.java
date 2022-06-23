package mcl.compiler.exceptions;

import mcl.compiler.source.CodeLocation;

public class MCLSyntaxError extends MCLError
{
    public MCLSyntaxError(CodeLocation start, CodeLocation end, String details)
    {
        super(start, end, "Syntax Error", details);
    }
}