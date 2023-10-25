package compiler.core.source;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SourceCollection
{
    final List<CodeSource> sources;
    
    public SourceCollection(CodeSource... sources)
    {
        this.sources = new ArrayList<>();
        for (CodeSource source : sources) if (!source.empty()) this.sources.add(source);
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
    public SourcePosition[] starts()
    {
        SourcePosition[] starts = new SourcePosition[sources.size()];
        for (int i = 0; i < sources.size(); i++) starts[i] = new SourcePosition(sources.get(i), 0, 0);
        return starts;
    }
    //endregion
}
