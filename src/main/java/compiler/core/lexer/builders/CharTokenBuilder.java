package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.source.SourcePosition;

public class CharTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> tokenType;
    private final char token;
    
    public CharTokenBuilder(Enum<?> tokenType, char token)
    {
        this.tokenType = tokenType;
        this.token = token;
    }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        if (position.valid() && position.getCharacter() == token)
        {
            SourcePosition start = position.copy();
            SourcePosition end = position.copy();
            position.advance();
            return new Token(tokenType, String.valueOf(token), start, end) { @Override public String toString() { return type.toString(); } };
        }
        return null;
    }
}
