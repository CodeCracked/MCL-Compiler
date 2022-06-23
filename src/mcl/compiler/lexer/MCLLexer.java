package mcl.compiler.lexer;

import mcl.compiler.MCLSourceCollection;
import mcl.compiler.exceptions.MCLLexicalException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MCLLexer
{
    //region Static
    private static final Set<Character> IGNORE_SET = Set.of(' ', '\t');
    private static final Set<TokenBuilder> TOKEN_BUILDERS = new HashSet<>();

    public static void registerTokenBuilder(TokenBuilder builder) { TOKEN_BUILDERS.add(builder); }
    //endregion

    private final MCLSourceCollection source;
    private int position;
    private Character currentChar;

    public MCLLexer(MCLSourceCollection source)
    {
        this.source = source;
        this.position = -1;
        this.currentChar = null;

        if (TOKEN_BUILDERS.size() == 0) TokenType.registerTokenBuilders();
        advance();
    }

    public MCLSourceCollection getSource() { return source; }
    public Character getCurrentChar() { return currentChar; }
    public void advance()
    {
        position++;
        currentChar = position < source.getSource().length() ? source.getSource().charAt(position) : null;
    }

    public List<Token<?>> makeTokens() throws MCLLexicalException
    {
        List<Token<?>> tokens = new ArrayList<>();

        while (currentChar != null)
        {
            if (IGNORE_SET.contains(currentChar))
            {
                advance();
                continue;
            }

            Token<?> token = null;
            for (TokenBuilder builder : TOKEN_BUILDERS)
            {
                token = builder.buildToken(this, position);
                if (token != null) break;
            }

            if (token != null) tokens.add(token);
            else
            {
                int startPosition = position;
                StringBuilder unknownTokenBuilder = new StringBuilder();
                while (currentChar != null && !IGNORE_SET.contains(currentChar))
                {
                    unknownTokenBuilder.append(currentChar);
                    advance();
                }
                throw new MCLLexicalException(unknownTokenBuilder.toString(), this, startPosition);
            }
        }

        return tokens;
    }
}
