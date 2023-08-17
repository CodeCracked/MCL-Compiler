import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
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
        Path testSource = Path.of("D:\\Git\\Minecraft\\MCL Compiler\\test\\mcl\\src");
        Path testDestination = Path.of("D:\\Git\\Minecraft\\MCL Compiler\\test\\mcl\\out");
        
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
        compileResult.displayIssues();
    }
    
    private static void tokenize(SourceCollection source)
    {
        // Tokenize
        Lexer lexer = MCL.lexer();
        Result<List<Token>> tokens = lexer.tokenize(source);
        if (tokens.getFailure() == null) tokens.get().forEach(IO.Debug::println);
        tokens.displayIssues();
    }
    
    private static void enumerateCharacters(SourceCollection source)
    {
        SourcePosition position = source.start();
        while (position.valid())
        {
            if (position.getCharacter() == '\n') IO.Debug.println(position + ": \\n");
            else IO.Debug.println(position + ": " + position.getCharacter());
            position.advance();
        }
    }
}