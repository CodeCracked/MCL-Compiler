package compiler.core.lexer;

import compiler.core.exceptions.UnknownTokenException;
import compiler.core.lexer.base.TokenType;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer
{
    private final AbstractTokenBuilder[] tokenBuilders;
    
    public Lexer(AbstractTokenBuilder... tokenBuilders)
    {
        this.tokenBuilders = tokenBuilders;
    }
    
    public Result<List<Token>> tokenize(SourceCollection source)
    {
        // Initialize
        SourcePosition position = source.start();
        List<Token> tokens = new ArrayList<>();
        
        while (position.valid())
        {
            // Build token
            Token token = null;
            for (AbstractTokenBuilder builder : tokenBuilders)
            {
                position.markPosition();
                token = builder.tryBuild(this, position);
                if (token != null)
                {
                    position.unmarkPosition();
                    break;
                }
                else position.revertPosition();
            }
            
            // Add token if successful, otherwise return a failed result
            if (token != null)
            {
                if (!token.type().equals(TokenType.IGNORED)) tokens.add(token);
            }
            else return Result.fail(new UnknownTokenException(position.copy()));
        }
        
        return Result.of(tokens);
    }
}
