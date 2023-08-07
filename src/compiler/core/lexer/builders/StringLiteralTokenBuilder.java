package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.base.LiteralTokenType;
import compiler.core.source.SourcePosition;

public class StringLiteralTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> tokenType;
    private final char rawStringCharacter;
    private final int minimumRawStringCharacters;
    
    public StringLiteralTokenBuilder(Enum<?> tokenType) { this(tokenType, '\'', -1); }
    public StringLiteralTokenBuilder(Enum<?> tokenType, char rawStringCharacter, int minimumRawStringCharacters)
    {
        this.tokenType = tokenType;
        this.rawStringCharacter = rawStringCharacter;
        this.minimumRawStringCharacters = minimumRawStringCharacters;
    }
    
    public static StringLiteralTokenBuilder restricted() { return new StringLiteralTokenBuilder(LiteralTokenType.STRING, '\'', -1); }
    public static StringLiteralTokenBuilder withRaw() { return new StringLiteralTokenBuilder(LiteralTokenType.STRING, '\'', 2); }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        // Normal String Literal
        if (position.getCharacter() == '"')
        {
            // Advance past opening quotes
            SourcePosition start = position.copy();
            if (!position.advance()) return null;
            
            // Read the string contents
            StringBuilder contents = new StringBuilder();
            boolean escapeNextCharacter = false;
            while (position.valid())
            {
                // Process current character
                if (escapeNextCharacter)
                {
                    contents.append(position.getCharacter());
                    escapeNextCharacter = false;
                }
                else
                {
                    if (position.getCharacter() == '\\') escapeNextCharacter = true;
                    else if (position.getCharacter() == '"') break;
                    else contents.append(position.getCharacter());
                }
                
                // Advance current position
                position.advance();
            }
            
            // Return the token
            SourcePosition end = position.copy();
            position.advance();
            return new Token(tokenType, contents.toString(), start, end);
        }

        // Raw String Literal
        else if (minimumRawStringCharacters > 0 && position.getCharacter() == rawStringCharacter)
        {
            position.markPosition();
            
            // Check for Raw String start
            int rawStringCharacters = countRawStringCharacters(position);
            if (rawStringCharacters < minimumRawStringCharacters)
            {
                position.revertPosition();
                return null;
            }
            
            // Read Raw String contents
            SourcePosition start = position.copy();
            StringBuilder contents = new StringBuilder();
            while (position.valid())
            {
                // Count current Raw String characters
                int currentRawCharacters = countRawStringCharacters(position);
                
                // If count matches the start count, end the Raw String
                if (currentRawCharacters == rawStringCharacters)
                {
                    SourcePosition end = position.copy(); end.retract();
                    return new Token(tokenType, contents.toString(), start, end);
                }
                
                // If count doesn't match, add them to the contents then add the current character to contents
                else
                {
                    contents.append(String.valueOf(rawStringCharacter).repeat(Math.max(0, currentRawCharacters)));
                    contents.append(position.getCharacter());
                    position.advance();
                }
            }
            
            return null;
        }
        
        // Not a String Literal
        else return null;
    }
    
    private int countRawStringCharacters(SourcePosition position)
    {
        int count = 0;
        while (position.valid() && position.getCharacter() == rawStringCharacter)
        {
            count++;
            position.advance();
        }
        return count;
    }
}
