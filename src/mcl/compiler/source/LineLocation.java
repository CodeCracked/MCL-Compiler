package mcl.compiler.source;

public class LineLocation
{
    protected final String file;
    protected final String lineContents;
    protected final int line;
    protected final int start;

    public LineLocation(String file, String lineContents, int line, int start)
    {
        this.file = file;
        this.lineContents = lineContents;
        this.line = line;
        this.start = start;
    }

    public String getFile() { return file; }
    public String getLineContents() { return lineContents; }
    public int getLine() { return line; }
    public int getStart() { return start; }
}
