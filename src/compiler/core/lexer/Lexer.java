package compiler.core.lexer;

import compiler.core.exceptions.UnknownTokenException;
import compiler.core.lexer.types.MetaTokenType;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer
{
    private final TokenBuilderList tokenBuilders;
    
    public Lexer(AbstractTokenBuilder... tokenBuilders)
    {
        this.tokenBuilders = new TokenBuilderList(tokenBuilders);
    }
    
    public Result<List<Token>> tokenize(SourceCollection source)
    {
        // Initialize
        Result<List<Token>> result = new Result<>();
        SourcePosition position = source.start();
        List<Token> tokens = new ArrayList<>();
        
        while (position.valid())
        {
            // Build token
            Token token = tokenBuilders.tryBuild(this, position);
            
            // If next token is unknown
            if (token == null)
            {
                SourcePosition unknownStart = position.copy();
                StringBuilder unknownContents = new StringBuilder();
                
                // Process first character
                unknownContents.append(position.getCharacter());
                position.advance();
                
                // Continue processing until a valid token is found
                Token valid = null;
                while (position.valid())
                {
                    // Try to find a known token
                    SourcePosition unknownEnd = position.copy();
                    valid = tokenBuilders.tryBuild(this, position);
                    
                    // If no known token exists
                    if (valid == null)
                    {
                        unknownContents.append(position.getCharacter());
                        position.advance();
                    }
                    
                    // Found known token
                    else
                    {
                        unknownEnd.retract();
                        Token unknownToken = new Token(MetaTokenType.UNKNOWN, unknownContents.toString(), unknownStart, unknownEnd);
                        tokens.add(unknownToken);
                        result.addError(new UnknownTokenException(unknownToken));
                        break;
                    }
                }
                
                // If no valid token was found
                if (valid == null)
                {
                    SourcePosition unknownEnd = position.copy(); unknownEnd.retract();
                    Token unknownToken = new Token(MetaTokenType.UNKNOWN, unknownContents.toString(), unknownStart, unknownEnd);
                    tokens.add(unknownToken);
                    result.addError(new UnknownTokenException(unknownToken));
                }
            }
            else if (!token.type().equals(MetaTokenType.IGNORED)) tokens.add(token);
        }
        
        return result.success(tokens);
    }
}
