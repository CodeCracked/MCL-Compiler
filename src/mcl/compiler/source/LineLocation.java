package mcl.compiler.source;

public class LineLocation
{
    protected final String file;
    protected final int line;
    protected final int start;

    public LineLocation(String file, int line, int start)
    {
        this.file = file;
        this.line = line;
        this.start = start;
    }

    public String getFile() { return file; }
    public int getLine() { return line; }
    public int getStart() { return start; }
}
