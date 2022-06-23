package mcl.compiler.exceptions;

import mcl.compiler.source.CodeLocation;

public class MCLError extends Exception
{
    protected final CodeLocation start;
    protected final CodeLocation end;
    protected final String errorName;
    protected final String details;

    public MCLError(CodeLocation start, CodeLocation end, String errorName, String details)
    {
        this.start = start;
        this.end = end;
        this.errorName = errorName;
        this.details = details;
    }

    @Override
    public String getMessage()
    {
        return toString();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s: %s\n", errorName, details));
        builder.append(String.format("File %s, Line %s Column %s\n", start.getFile(), start.getLine(), start.getColumn()));
        return builder.toString();
    }
}
