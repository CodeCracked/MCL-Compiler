import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.IO;
import compiler.core.util.Result;
import mcl.lexer.TokenType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Path testPath = Path.of("D:\\Git\\Minecraft\\MCL Compiler\\test\\mcl");
        
        try
        {
            SourceCollection source = SourceCollection.fromDirectory(testPath, ".mcl");
            tokenize(source);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    private static void tokenize(SourceCollection source)
    {
        // Create Lexer
        Lexer<TokenType> lexer = new Lexer<>(true, new AbstractTokenBuilder<>()
        {
            @Override
            public Token<TokenType> tryBuild(Lexer<TokenType> lexer)
            {
                SourcePosition start = lexer.getPosition();
                
                StringBuilder content = new StringBuilder();
                while (!Character.isWhitespace(lexer.getCharacter()))
                {
                    content.append(lexer.getCharacter());
                    if (!lexer.advance()) break;
                }
                
                return content.length() > 0 ? new Token<>(TokenType.TEST, content.toString(), start, lexer.getPosition()) : null;
            }
        });
        
        // Tokenize
        Result<List<Token<TokenType>>> tokens = lexer.tokenize(source);
        if (tokens.error() != null) tokens.displayIssues();
        else tokens.get().forEach(IO.Debug::println);
    }
    
    private static void enumerateCharacters(SourceCollection source)
    {
        SourcePosition pos = source.start();
        do
        {
            char c = source.charAt(pos);
            String str = (c == '\n') ? "\\n" : Character.toString(c);
            IO.Debug.println(pos + ": " + str);
        }
        while ((pos = source.advance(pos)) != null);
    }
}