package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourcePosition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IdentifierTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> identifierType;
    private final String firstCharacters;
    private final String bodyCharacters;
    private final Map<String, Enum<?>> keywords;
    
    public IdentifierTokenBuilder(Enum<?> identifierType, String firstCharacters, String bodyCharacters, Enum<?>[] keywords, Function<Enum<?>, String> keywordMapper)
    {
        this.identifierType = identifierType;
        this.firstCharacters = firstCharacters;
        this.bodyCharacters = bodyCharacters;
        
        this.keywords = new HashMap<>();
        if (keywordMapper == null) keywordMapper = keyword -> keyword.name().toLowerCase();
        for (Enum<?> keyword : keywords) this.keywords.put(keywordMapper.apply(keyword), keyword);
    }
    
    public static IdentifierTokenBuilder camelCase(Enum<?>[] keywords, Function<Enum<?>, String> keywordMapper) { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, "abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz1234567890_", keywords, keywordMapper); }
    public static IdentifierTokenBuilder normal(Enum<?>[] keywords, Function<Enum<?>, String> keywordMapper) { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_", keywords, keywordMapper); }
    
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
        
        // Check For Keyword
        if (keywords.containsKey(tokenContent))
        {
            Enum<?> keyword = keywords.get(tokenContent);
            return new Token(keyword, tokenContent, start, end) { @Override public String toString() { return type.toString(); } };
        }
        else return new Token(identifierType, tokenContent, start, end);
    }
}
