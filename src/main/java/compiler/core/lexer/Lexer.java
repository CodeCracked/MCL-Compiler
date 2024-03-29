package compiler.core.lexer;

import compiler.core.lexer.types.MetaTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourceCollection;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnknownTokenException;

import java.util.ArrayList;
import java.util.List;

public class Lexer
{
    private final TokenType endOfFile;
    private final TokenBuilderList tokenBuilders;
    
    public Lexer(AbstractTokenBuilder... tokenBuilders) { this(TokenType.END_OF_FILE, tokenBuilders); }
    public Lexer(TokenType endOfFile, AbstractTokenBuilder... tokenBuilders)
    {
        this.endOfFile = endOfFile;
        this.tokenBuilders = new TokenBuilderList(tokenBuilders);
    }
    
    public Result<List<Token>[]> tokenize(SourceCollection source)
    {
        // Initialize
        Result<List<Token>[]> result = new Result<>();
        SourcePosition[] positions = source.starts();
        List<Token>[] tokenizations = new List[positions.length];
        
        // Tokenize each start position
        for (int i = 0; i < positions.length; i++)
        {
            tokenizations[i] = result.register(tokenizeSource(positions[i]));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(tokenizations);
    }
    private Result<List<Token>> tokenizeSource(SourcePosition position)
    {
        Result<List<Token>> result = new Result<>();
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
    
        // Insert end-of-file token
        if (endOfFile != null) tokens.add(new Token(endOfFile, "end-of-file", position.copy(), position.copy())
        {
            @Override
            public String toString()
            {
                return type.name();
            }
        });
    
        return result.success(tokens);
    }
}
