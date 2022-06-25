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

    private String getArrowsString(String line, int start, int end)
    {
        StringBuilder builder = new StringBuilder();

        char[] lineChars = line.toCharArray();
        for (int i = 0; i < start; i++)
        {
            if (lineChars[i] == '\t') builder.append("    ");
            else builder.append(' ');
        }

        for (int i = start; i < Math.min(end, lineChars.length); i++)
        {
            if (lineChars[i] == '\t') builder.append("^^^^");
            else builder.append('^');
        }
        builder.append("^".repeat(Math.max(0, end - Math.min(end, lineChars.length))));

        return builder.toString();
    }

    @Override
    public String toString()
    {
        return String.format("%s: %s\n", errorName, details) +
                String.format("File %s, Line %s Column %s\n", start.getFile(), start.getLine(), start.getColumn()) +
                String.format("\n%s\n", start.getLineContents().replace("\t", "    ")) +
                getArrowsString(start.getLineContents(), start.getColumn(), end.getLine() > start.getLine() ? start.getColumn() + 1 : end.getColumn()) +
                '\n';
    }
}
