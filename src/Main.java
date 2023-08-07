import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.base.GrammarTokenType;
import compiler.core.lexer.base.LiteralTokenType;
import compiler.core.lexer.base.TokenType;
import compiler.core.lexer.builders.*;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.IO;
import compiler.core.util.Result;
import mcl.lexer.KeywordLists;

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
        Lexer lexer = new Lexer
        (
                WhitespaceTokenBuilder.ignore(),
                CommentTokenBuilder.ignore(),
                NumberLiteralsTokenBuilder.normal(),
                StringLiteralTokenBuilder.withRaw(),
        
                new CharTokenBuilder(GrammarTokenType.LBRACE, '{'),
                new CharTokenBuilder(GrammarTokenType.RBRACE, '}'),
                new CharTokenBuilder(GrammarTokenType.LPAREN, '('),
                new CharTokenBuilder(GrammarTokenType.RPAREN, ')'),
                new CharTokenBuilder(GrammarTokenType.LBRACKET, '['),
                new CharTokenBuilder(GrammarTokenType.RBRACKET, ']'),
                new CharTokenBuilder(GrammarTokenType.SEMICOLON, ';'),
                new CharTokenBuilder(GrammarTokenType.COLON, ':'),
                new CharTokenBuilder(GrammarTokenType.COMMA, ','),
                new CharTokenBuilder(GrammarTokenType.DOT, '.'),
                
                IdentifierTokenBuilder.camelCase(KeywordLists.KEYWORDS)
        );
        
        // Tokenize
        Result<List<Token>> tokens = lexer.tokenize(source);
        if (tokens.error() != null) tokens.displayIssues();
        else tokens.get().forEach(IO.Debug::println);
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