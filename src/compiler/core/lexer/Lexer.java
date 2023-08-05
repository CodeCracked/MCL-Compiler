package compiler.core.lexer;

import compiler.core.exceptions.UnknownTokenException;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer<T>
{
    private final AbstractTokenBuilder<T>[] tokenBuilders;
    private final boolean ignoreWhitespace;
    
    private SourceCollection source;
    private SourcePosition position;
    private char character;
    
    @SafeVarargs
    public Lexer(boolean ignoreWhitespace, AbstractTokenBuilder<T>... tokenBuilders)
    {
        this.ignoreWhitespace = ignoreWhitespace;
        this.tokenBuilders = tokenBuilders;
    }
    
    public SourcePosition getPosition() { return this.position; }
    public void setPosition(SourcePosition position) { this.position = position; }
    public char getCharacter() { return character; }
    public boolean advance()
    {
        this.position = this.source.advance(this.position);
        if (this.position == null) return false;
        
        this.character = this.source.charAt(this.position);
        return true;
    }
    
    public Result<List<Token<T>>> tokenize(SourceCollection source)
    {
        // Initialize
        this.source = source;
        this.position = source.start();
        this.character = this.source.charAt(this.position);
        
        List<Token<T>> tokens = new ArrayList<>();
        while (position != null)
        {
            // Skip Whitespace
            if (ignoreWhitespace)
            {
                while (position != null && Character.isWhitespace(source.charAt(position))) advance();
                if (position == null) break;
            }
            
            // Build token
            Token<T> token = null;
            for (AbstractTokenBuilder<T> builder : tokenBuilders)
            {
                token = builder.tryBuild(this);
                if (token != null) break;
            }
            
            // Add token if successful, otherwise return a failed result
            if (token != null) tokens.add(token);
            else return Result.fail(new UnknownTokenException(this.source, this.position));
        }
        
        return Result.of(tokens);
    }
}
