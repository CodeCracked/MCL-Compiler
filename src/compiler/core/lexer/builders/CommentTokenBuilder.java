package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.MetaTokenType;
import compiler.core.source.SourcePosition;

public class CommentTokenBuilder extends AbstractTokenBuilder
{
    private final Enum<?> tokenType;
    private final String lineComment;
    private final String blockCommentStart;
    private final String blockCommentEnd;
    
    public CommentTokenBuilder(Enum<?> tokenType, String lineComment, String blockCommentStart, String blockCommentEnd)
    {
        this.tokenType = tokenType;
        this.lineComment = lineComment;
        this.blockCommentStart = blockCommentStart;
        this.blockCommentEnd = blockCommentEnd;
    }
    
    public static CommentTokenBuilder preserve() { return new CommentTokenBuilder(MetaTokenType.COMMENT, "//", "/*", "*/"); }
    public static CommentTokenBuilder ignore() { return new CommentTokenBuilder(MetaTokenType.IGNORED, "//", "/*", "*/"); }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        SourcePosition start = position.copy();
        
        // Check Line Comment
        position.markPosition();
        boolean isLineComment = true;
        for (int i = 0; i < lineComment.length(); i++)
        {
            if (!position.valid() || position.getCharacter() != lineComment.charAt(i))
            {
                isLineComment = false;
                break;
            }
            else position.advance();
        }
        
        // Handle Line Comment
        if (isLineComment)
        {
            position.unmarkPosition();
            int line = start.getLine();
            int source = start.getSourceIndex();
            
            StringBuilder contents = new StringBuilder();
            while (position.valid() && position.getLine() == line && position.getSourceIndex() == source)
            {
                contents.append(position.getCharacter());
                position.advance();
            }
            
            SourcePosition end = position.copy(); end.retract();
            return new Token(tokenType, contents.substring(0, contents.length() - 1), start, end);
        }
        else position.revertPosition();
        
        // Check Block Comment
        boolean isBlockComment = true;
        for (int i = 0; i < blockCommentStart.length(); i++)
        {
            if (!position.valid() || position.getCharacter() != blockCommentStart.charAt(i))
            {
                isBlockComment = false;
                break;
            }
            else position.advance();
        }
        
        // Handle Block Comment
        if (isBlockComment)
        {
            int currentEndIndex = 0;
            StringBuilder contents = new StringBuilder();
            
            while (position.valid())
            {
                contents.append(position.getCharacter());
                if (position.getCharacter() == blockCommentEnd.charAt(currentEndIndex))
                {
                    currentEndIndex++;
                    position.advance();
    
                    if (currentEndIndex >= blockCommentEnd.length())
                    {
                        SourcePosition end = position.copy(); end.retract();
                        return new Token(tokenType, contents.substring(0, contents.length() - blockCommentEnd.length()), start, end);
                    }
                }
                else
                {
                    currentEndIndex = 0;
                    position.advance();
                }
            }
        }
        
        // No Comment
        return null;
    }
}
