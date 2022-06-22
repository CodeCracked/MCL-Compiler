package mcl.compiler;

import mcl.compiler.exceptions.MCLException;

import java.io.File;
import java.io.IOException;

public class MCLCompiler
{
    public void compile(File source, File target) throws IOException, MCLException
    {
        MCLSourceCollection sourceCollection = new MCLSourceCollection(source);
    }
}
