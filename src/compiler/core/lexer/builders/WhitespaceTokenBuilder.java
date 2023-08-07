package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.base.GrammarTokenType;
import compiler.core.lexer.base.TokenType;
import compiler.core.source.SourcePosition;

public class WhitespaceTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> whitespaceTokenType;
    private final Enum<?> newlineTokenType;
    
    public WhitespaceTokenBuilder(Enum<?> tokenType) { this(tokenType, null); }
    public WhitespaceTokenBuilder(Enum<?> whitespaceTokenType, Enum<?> newlineTokenType)
    {
        this.whitespaceTokenType = whitespaceTokenType;
        this.newlineTokenType = newlineTokenType;
    }
    
    public static WhitespaceTokenBuilder preserve() { return new WhitespaceTokenBuilder(GrammarTokenType.WHITESPACE, GrammarTokenType.NEWLINE); }
    public static WhitespaceTokenBuilder ignore() { return new WhitespaceTokenBuilder(TokenType.IGNORED); }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        // Separate Newline Token
        if (newlineTokenType != null)
        {
            if (position.getCharacter() == '\n')
            {
                SourcePosition start = position.copy();
                SourcePosition end = position.copy();
                position.advance();
                return new Token(newlineTokenType, "\\n", start, end);
            }
            else if (Character.isWhitespace(position.getCharacter()))
            {
                SourcePosition start = position.copy();
                StringBuilder contents = new StringBuilder();
                while (position.valid() && Character.isWhitespace(position.getCharacter()) && position.getCharacter() != '\n')
                {
                    contents.append(position.getCharacter());
                    position.advance();
                }
                SourcePosition end = position.copy(); end.retract();
                return new Token(whitespaceTokenType, contents.toString(), start, end);
            }
            else return null;
        }
        
        // No Newline Token
        else
        {
            if (Character.isWhitespace(position.getCharacter()))
            {
                SourcePosition start = position.copy();
                StringBuilder contents = new StringBuilder();
                while (position.valid() && Character.isWhitespace(position.getCharacter()))
                {
                    contents.append(position.getCharacter());
                    position.advance();
                }
                SourcePosition end = position.copy(); end.retract();
                return new Token(whitespaceTokenType, contents.toString(), start, end);
            }
            else return null;
        }
    }
}
