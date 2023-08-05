import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;

import java.io.IOException;
import java.nio.file.Path;

public class Main
{
    public static void main(String[] args)
    {
        Path testPath = Path.of("D:\\Git\\Minecraft\\MCL Compiler\\test\\mcl");
        
        try
        {
            SourceCollection source = SourceCollection.fromDirectory(testPath, ".mcl");
            SourcePosition pos = source.start();
            do
            {
                char c = source.charAt(pos);
                String str = (c == '\n') ? "\\n" : Character.toString(c);
                System.out.println(pos + ": " + str);
            }
            while ((pos = source.advance(pos)) != null);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}