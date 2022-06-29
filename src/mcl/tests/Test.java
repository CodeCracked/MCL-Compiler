package mcl.tests;

import mcl.compiler.CompilerConfig;
import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Test
{
    public static void main(String[] args)
    {
        Path source = new File("testing/src").toPath();
        Path dest = new File("testing/out").toPath();

        try
        {
            MCLCompiler compiler = new MCLCompiler(new CompilerConfig());

            compiler.compile(source.resolve("std").toFile(), dest.resolve("std").toFile());
            Files.copy(dest.resolve("std").resolve("mcl.mclh"), source.resolve("tests").resolve("std.mclh"), StandardCopyOption.REPLACE_EXISTING);
            compiler.compile(source.resolve("tests").toFile(), dest.resolve("tests").toFile());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
