package mcl.compiler.source;

public class CodeLocation extends LineLocation
{
    private final MCLSourceCollection source;
    private final int column;

    public CodeLocation(MCLSourceCollection source, LineLocation line, int position)
    {
        super(line.file, line.lineContents, line.line, line.start);
        this.source = source;
        this.column = position - line.start;
    }

    public int getColumn() { return column; }

    @Override
    public String toString()
    {
        return String.format("%s(Line %s Column %s)", file, line, column);
    }
}