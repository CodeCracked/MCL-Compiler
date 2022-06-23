package mcl.tests;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;

import java.io.File;

public class Test
{
    public static void main(String[] args)
    {
        File source = new File("testing/src");
        File dest = new File("testing/out");

        try
        {
            MCLCompiler compiler = new MCLCompiler();
            compiler.compile(source, dest);
        }
        catch (MCLError e)
        {
            //System.err.println(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
