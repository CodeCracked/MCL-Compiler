package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourcePosition;

import java.util.Set;

public class IdentifierTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> identifierType;
    private final Enum<?> keywordType;
    private final String firstCharacters;
    private final String bodyCharacters;
    private final Set<String> keywords;
    
    public IdentifierTokenBuilder(Enum<?> identifierType, Enum<?> keywordType, String firstCharacters, String bodyCharacters, Set<String> keywords)
    {
        this.identifierType = identifierType;
        this.keywordType = keywordType;
        this.firstCharacters = firstCharacters;
        this.bodyCharacters = bodyCharacters;
        this.keywords = keywords;
    }
    
    public static  IdentifierTokenBuilder camelCase(Set<String> keywords) { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, TokenType.KEYWORD, "abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz1234567890_", keywords); }
    public static  IdentifierTokenBuilder normal(Set<String> keywords) { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, TokenType.KEYWORD, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_", keywords); }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        // Initialize
        SourcePosition start = position.copy();
        StringBuilder contents = new StringBuilder();
        
        // Match First Character
        if (firstCharacters.indexOf(position.getCharacter()) < 0) return null;
        else contents.append(position.getCharacter());
        position.advance();
        
        // Match Body Characters
        while (position.valid() && bodyCharacters.indexOf(position.getCharacter()) >= 0)
        {
            contents.append(position.getCharacter());
            position.advance();
        }
        
        // Return Token
        String tokenContent = contents.toString();
        SourcePosition end = position.copy(); end.retract();
        if (keywords.contains(tokenContent)) return new Token(keywordType, tokenContent, start, end);
        else return new Token(identifierType, tokenContent, start, end);
    }
}
