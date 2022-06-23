package mcl.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MCLSourceCollection
{
    public static class LineLocation
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
    public static class CodeLocation extends LineLocation
    {
        private final int column;

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

    public static final char LINE_SEPARATOR = '\n';

    private String source = "";
    private List<LineLocation> lineLocations;

    public MCLSourceCollection(File source) throws IOException
    {
        lineLocations = new ArrayList<>();

        System.out.println("Source Collection: ");
        if (source.isDirectory())
        {
            List<Path> files = Files.find(source.toPath(), 999, (path, attributes) -> attributes.isRegularFile()).collect(Collectors.toList());
            for (Path file : files) if (file.toFile().getName().toLowerCase().endsWith(".mcl")) addSourceFile(source.toPath(), file);
        }
        else addSourceFile(null, source.toPath());

        this.source = this.source.trim();
        System.out.println();
    }

    private void addSourceFile(Path root, Path path) throws IOException
    {
        String file = root == null ? path.toString() : path.toString().replace(root + File.separator, "");

        System.out.println(file);
        List<String> lines = Files.readAllLines(path);

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            String trimmed = line.trim();
            if (trimmed.length() > 0)
            {
                lineLocations.add(new LineLocation(file, i + 1, source.length()));
                source += line + LINE_SEPARATOR;
            }
        }
    }

    public String getSource()
    {
        return source;
    }
    public LineLocation getLineLocation(int line)
    {
        return lineLocations.get(line);
    }
    public LineLocation getLastLineLocation()
    {
        return lineLocations.get(lineLocations.size() - 1);
    }
    public CodeLocation getCodeLocation(int codePosition)
    {
        for (int i = 1; i <= lineLocations.size(); i++)
        {
            if (i == lineLocations.size() || lineLocations.get(i).start > codePosition)
            {
                LineLocation lineLocation = lineLocations.get(i - 1);
                return new CodeLocation(lineLocation, codePosition);
            }
        }
        return new CodeLocation(getLastLineLocation(), codePosition);
    }
}
