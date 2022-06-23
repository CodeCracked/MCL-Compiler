package mcl.compiler.source;

public class CodeLocation extends LineLocation
{
    private int column;

    public CodeLocation(LineLocation line, int position)
    {
        super(line.file, line.line, line.start);
        this.column = position - line.start;
    }

    public int getColumn() { return column; }

    @Override
    public String toString()
    {
        return String.format("%s(Line %s Column %s)", file, line, column);
    }
}