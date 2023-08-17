package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourcePosition;

public class IdentifierTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> identifierType;
    private final String firstCharacters;
    private final String bodyCharacters;
    
    public IdentifierTokenBuilder(Enum<?> identifierType, String firstCharacters, String bodyCharacters)
    {
        this.identifierType = identifierType;
        this.firstCharacters = firstCharacters;
        this.bodyCharacters = bodyCharacters;
    }
    
    public static  IdentifierTokenBuilder camelCase() { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, "abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz1234567890_"); }
    public static  IdentifierTokenBuilder normal() { return new IdentifierTokenBuilder(TokenType.IDENTIFIER, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_"); }
    
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
        return new Token(identifierType, tokenContent, start, end);
    }
}
