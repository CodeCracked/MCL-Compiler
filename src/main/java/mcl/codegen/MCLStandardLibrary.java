package mcl.codegen;

import compiler.core.source.CodeSource;
import compiler.core.source.SourceCollection;
import compiler.core.util.IO;
import compiler.core.util.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class MCLStandardLibrary
{
    public interface ResourceConsumer
    {
        void accept(Path root, Path resource, Path relative) throws IOException;
    }
    
    public static final String NAME = "stdlib";
    
    //region Installation
    public static Result<SourceCollection> installHeaders(SourceCollection source)
    {
        Result<SourceCollection> result = new Result<>();
        
        // Prepend all MCL source files in the standard library
        result.register(forEachResource("/" + NAME + "/source", (root, resource, relative) ->
        {
            try (InputStream resourceStream = Files.newInputStream(resource))
            {
                IO.Debug.println(relative.toString() + ": " + resource.isAbsolute());
                source.prependSource(new CodeSource.Stream(relative.toString(), resourceStream) { @Override public boolean isLibrary() { return true; } });
            }
        }, resource -> resource.toString().toLowerCase().endsWith(".mcl")));
        if (result.getFailure() != null) return result;
        
        return result.success(source);
    }
    public static Result<Void> installNatives(Path directory)
    {
        Result<Void> result = new Result<>();
        
        // Copy all native files in the standard library
        result.register(forEachResource("/" + NAME + "/native", (root, resource, relative) ->
        {
            Path destination = directory.resolve(relative.toString()).toAbsolutePath();
            if (destination.getFileName().toString().contains("."))
            {
                if (!destination.getParent().toFile().exists()) destination.getParent().toFile().mkdirs();
                if (!destination.getParent().toFile().isFile()) destination.toFile().createNewFile();
                try (FileOutputStream destinationStream = new FileOutputStream(destination.toFile())) { Files.copy(resource, destinationStream); }
            }
        }, resource -> true));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
    //endregion
    //region Private Helpers
    private static Result<Void> forEachResource(String directoryPath, ResourceConsumer consumer, Predicate<Path> predicate)
    {
        Result<Void> result = new Result<>();
        FileSystem fileSystem = null;
        
        try
        {
            URI uri = Objects.requireNonNull(MCLStandardLibrary.class.getResource(directoryPath)).toURI();
            Path root;
            
            if (uri.getScheme().equals("jar"))
            {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                root = fileSystem.getPath(directoryPath);
            }
            else root = Paths.get(uri);
            
            try (Stream<Path> walk = Files.walk(root))
            {
                for (Iterator<Path> iterator = walk.iterator(); iterator.hasNext();)
                {
                    Path resource = iterator.next();
                    if (predicate.test(resource)) consumer.accept(root, resource, root.relativize(resource));
                }
            }
        }
        catch (Exception e)
        {
            result.failure(e);
        }
        finally
        {
            if (fileSystem != null)
            {
                try { fileSystem.close(); }
                catch (Exception e) { result.failure(e); }
            }
        }
        
        return result;
    }
    private static Result<Void> extract(Path directory)
    {
        // Open Standard Library Resource
        try (ZipInputStream zipStream = new ZipInputStream(Objects.requireNonNull(MCLStandardLibrary.class.getResourceAsStream("/" + NAME + ".zip"))))
        {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
        
            // For Each Entry
            while ((entry = zipStream.getNextEntry()) != null)
            {
                if (entry.isDirectory()) continue;
            
                // Configure Entry Destination
                File destination = directory.resolve(entry.getName()).toFile();
                File parent = destination.getParentFile();
                if (parent != null) parent.mkdirs();
            
                // Extract File
                try (FileOutputStream destinationStream = new FileOutputStream(destination))
                {
                    while (zipStream.available() > 0)
                    {
                        int length = zipStream.read(buffer);
                        if (length <= 0) continue;
                        destinationStream.write(buffer, 0, length);
                    }
                }
            }
        
            return Result.of(null);
        }
        catch (Exception e)
        {
            return Result.fail(e);
        }
    }
    //endregion
}
