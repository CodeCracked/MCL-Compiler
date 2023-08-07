package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.source.SourcePosition;

public class MatchingTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> tokenType;
    private final String template;
    
    public MatchingTokenBuilder(Enum<?> tokenType, String template)
    {
        this.tokenType = tokenType;
        this.template = template;
    }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        SourcePosition start = position.copy();
        
        // Match Template
        for (int i = 0; i < template.length(); i++)
        {
            if (!position.valid() || position.getCharacter() != template.charAt(i)) return null;
            else position.advance();
        }
        
        SourcePosition end = position.copy(); end.retract();
        return new Token(tokenType, template, start, end) { @Override public String toString() { return type.toString(); } };
    }
}
