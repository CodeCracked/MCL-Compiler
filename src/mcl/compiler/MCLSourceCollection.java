package mcl.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MCLSourceCollection
{
    public class LineLocation
    {
        private String file;
        private int line;
        private int start;

        public LineLocation(String file, int line, int start)
        {
            this.file = file;
            this.line = line;
            this.start = start;
        }

        public String getFile()
        {
            return file;
        }
        public int getLine()
        {
            return line;
        }
        public int getStart()
        {
            return start;
        }
    }

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
                source += line + '\n';
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
}
