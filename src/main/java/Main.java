import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.source.SourceCollection;
import compiler.core.util.IO;
import compiler.core.util.Result;
import mcl.MCL;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Path testSource = Path.of("test/mcl/src");
        Path testDestination = Path.of("test/mcl/out");
        
        try
        {
            SourceCollection source = SourceCollection.fromDirectory(testSource, ".mcl");
            compile(source, testDestination);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    private static void compile(SourceCollection source, Path destination)
    {
        Result<Void> compileResult = MCL.compiler().compile(source, destination);
        compileResult.displayIssues(false);
    }
    
    private static void tokenize(SourceCollection source)
    {
        // Tokenize
        Lexer lexer = MCL.lexer();
        Result<List<Token>[]> tokens = lexer.tokenize(source);
        if (tokens.getFailure() == null)
        {
            for (List<Token> file : tokens.get())
            {
                file.forEach(IO.Debug::println);
                IO.Debug.println();
            }
        }
        tokens.displayIssues(false);
    }
}