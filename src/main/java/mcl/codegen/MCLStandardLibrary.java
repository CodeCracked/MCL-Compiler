package mcl.codegen;

import compiler.core.source.CodeSource;
import compiler.core.source.SourceCollection;
import compiler.core.util.Result;
import mcl.MCL;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class MCLStandardLibrary
{
    public static final String NAME = "stdlib";
    
    public static Result<SourceCollection> installHeaders(SourceCollection source)
    {
        try
        {
            source.addSource(new CodeSource.Resource(MCLStandardLibrary.class, "/" + NAME + ".mcl"));
            return Result.of(source);
        }
        catch (Exception e) { return Result.fail(e); }
    }
    public static Result<Void> installNatives(Path directory)
    {
        Result<Void> result = new Result<>();
        
        // Extract Standard Library
        result.register(extract(directory));
        if (result.getFailure() != null) return result;
        
        return result.success(null);
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
}
