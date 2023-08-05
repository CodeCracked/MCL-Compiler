package compiler.core.source;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SourceCollection
{
    private final CodeSource[] sources;
    
    public SourceCollection(CodeSource... sources)
    {
        this.sources = sources;
    }
    
    //region Builders
    public static SourceCollection fromStrings(String... code)
    {
        CodeSource[] sources = new CodeSource[code.length];
        for (int i = 0; i < sources.length; i++) sources[i] = new CodeSource.Literal(code[i]);
        return new SourceCollection(sources);
    }
    
    public static SourceCollection fromDirectory(Path directory) throws IOException { return fromDirectory(directory, file -> true); }
    public static SourceCollection fromDirectory(Path directory, String firstExtension, String... otherExtensions) throws IOException
    {
        Set<String> extensions = new HashSet<>();
        extensions.add(firstExtension);
        Collections.addAll(extensions, otherExtensions);
        
        return fromDirectory(directory, file ->
        {
            String extension = file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
            return extensions.contains(extension);
        });
    }
    public static SourceCollection fromDirectory(Path directory, Predicate<File> predicate) throws IOException
    {
        try (Stream<Path> paths = Files.find(directory, Integer.MAX_VALUE, (path, attributes) -> attributes.isRegularFile() && predicate.test(path.toFile())))
        {
            Path[] sourceFiles = paths.toArray(Path[]::new);
            CodeSource[] sources = new CodeSource[sourceFiles.length];
            for (int i = 0; i < sources.length; i++) sources[i] = new CodeSource.File(sourceFiles[i], directory);
            return new SourceCollection(sources);
        }
    }
    //endregion
    //region Public Methods
    public SourcePosition start()
    {
        return new SourcePosition(sources[0], 0, 0, 0);
    }
    public SourcePosition advance(SourcePosition position)
    {
        // Get the next position in the same source
        SourcePosition next = position.source.advance(position);
        
        // If the current source doesn't have a next position
        if (next == null)
        {
            // Try to return the starting position of the next source
            int nextIndex = position.sourceIndex + 1;
            return nextIndex < sources.length ? new SourcePosition(sources[nextIndex], nextIndex, 0, 0) : null;
        }
        
        // Return the next position
        else return next;
    }
    public char charAt(SourcePosition position)
    {
        return position.source.getCharAt(position);
    }
    //endregion
}
