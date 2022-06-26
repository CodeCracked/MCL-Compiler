package mcl.compiler.source;

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
    public static final char LINE_SEPARATOR = '\n';
    public Path openHeader;

    private String source = "";
    private final List<LineLocation> lineLocations;

    private final List<Path> headers;
    private final Map<Path, List<LineLocation>> headerLineLocations;
    private final Map<Path, String> headerSources;

    public MCLSourceCollection(File source, boolean debugPrint) throws IOException
    {
        lineLocations = new ArrayList<>();
        headers = new ArrayList<>();
        headerLineLocations = new HashMap<>();
        headerSources = new HashMap<>();

        if (debugPrint) System.out.println("Source Collection: ");
        if (source.isDirectory())
        {
            List<Path> files = Files.find(source.toPath(), 999, (path, attributes) -> attributes.isRegularFile()).collect(Collectors.toList());
            for (Path file : files)
            {
                if (file.toFile().getName().toLowerCase().endsWith(".mcl")) addSourceFile(source.toPath(), file, false, debugPrint);
                else if (file.toFile().getName().toLowerCase().endsWith(".mclh")) addSourceFile(source.toPath(), file, true, debugPrint);
            }
        }
        else addSourceFile(null, source.toPath(), false, debugPrint);

        this.source = this.source.trim();
        if (debugPrint) System.out.println();
    }

    private void addSourceFile(Path root, Path path, boolean header, boolean debugPrint) throws IOException
    {
        String file = root == null ? path.toString() : path.toString().replace(root + File.separator, "");

        if (debugPrint) System.out.println(file);
        List<String> lines = Files.readAllLines(path);

        StringBuilder source = new StringBuilder();
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            String trimmed = line.trim();
            if (trimmed.length() > 0)
            {
                LineLocation lineLocation = new LineLocation(file, line, i + 1, source.length());
                source.append(line);
                source.append(LINE_SEPARATOR);

                if (header)
                {
                    if (!headerLineLocations.containsKey(path)) headerLineLocations.put(path, new ArrayList<>());
                    headerLineLocations.get(path).add(lineLocation);
                    headers.add(path);
                }
                else lineLocations.add(lineLocation);
            }
        }

        if (header) headerSources.put(path, source.toString());
        else this.source = source.toString();
    }

    public String getSource()
    {
        String source = this.source;
        if (openHeader != null) source = headerSources.get(openHeader);
        return source;
    }
    public List<Path> getHeaders()
    {
        return headers;
    }

    public LineLocation getLineLocation(int line)
    {
        List<LineLocation> lineLocations = this.lineLocations;
        if (openHeader != null) lineLocations = headerLineLocations.get(openHeader);
        return lineLocations.get(line);
    }
    public LineLocation getLastLineLocation()
    {
        List<LineLocation> lineLocations = this.lineLocations;
        if (openHeader != null) lineLocations = headerLineLocations.get(openHeader);
        return lineLocations.get(lineLocations.size() - 1);
    }
    public CodeLocation getCodeLocation(int codePosition)
    {
        List<LineLocation> lineLocations = this.lineLocations;
        if (openHeader != null) lineLocations = headerLineLocations.get(openHeader);

        for (int i = 1; i <= lineLocations.size(); i++)
        {
            if (i == lineLocations.size() || lineLocations.get(i).start > codePosition)
            {
                LineLocation lineLocation = lineLocations.get(i - 1);
                return new CodeLocation(this, lineLocation, codePosition);
            }
        }
        return new CodeLocation(this, getLastLineLocation(), codePosition);
    }
}
