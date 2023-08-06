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
    
    @SafeVarargs
    public Lexer(boolean ignoreWhitespace, AbstractTokenBuilder<T>... tokenBuilders)
    {
        this.ignoreWhitespace = ignoreWhitespace;
        this.tokenBuilders = tokenBuilders;
    }
    
    public Result<List<Token<T>>> tokenize(SourceCollection source)
    {
        // Initialize
        SourcePosition position = source.start();
        List<Token<T>> tokens = new ArrayList<>();
        
        while (position.valid())
        {
            // Skip Whitespace
            if (ignoreWhitespace)
            {
                while (position.valid() && Character.isWhitespace(position.getCharacter())) position.advance();
                if (!position.valid()) break;
            }
            
            // Build token
            Token<T> token = null;
            for (AbstractTokenBuilder<T> builder : tokenBuilders)
            {
                token = builder.tryBuild(this, position);
                if (token != null) break;
            }
            
            // Add token if successful, otherwise return a failed result
            if (token != null) tokens.add(token);
            else return Result.fail(new UnknownTokenException(position.copy()));
        }
        
        return Result.of(tokens);
    }
}
