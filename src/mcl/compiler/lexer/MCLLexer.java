package mcl.compiler.lexer;

import mcl.compiler.exceptions.MCLLexicalError;
import mcl.compiler.source.MCLSourceCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MCLLexer
{
    //region Static
    private static final Set<Character> IGNORE_SET = Set.of(' ', '\t');
    private static final List<TokenBuilder> TOKEN_BUILDERS = new ArrayList<>();

    public static void init()
    {
        if (TOKEN_BUILDERS.size() == 0) TokenType.registerTokenBuilders();
    }
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
        advance();
    }

    public MCLSourceCollection getSource() { return source; }
    public int getPosition() { return position; }
    public Character getCurrentChar() { return currentChar; }

    public void setPosition(int position)
    {
        this.position = position;
        currentChar = position < source.getSource().length() ? source.getSource().charAt(position) : null;
    }
    public void advance()
    {
        position++;
        currentChar = position < source.getSource().length() ? source.getSource().charAt(position) : null;
    }

    public LexerResult makeTokens()
    {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != null)
        {
            if (IGNORE_SET.contains(currentChar))
            {
                advance();
                continue;
            }

            Token token = null;
            for (TokenBuilder builder : TOKEN_BUILDERS)
            {
                token = builder.buildToken(this, position);
                if (token != null) break;
            }

            if (token != null)
            {
                if (token.type() != TokenType.INTERNAL_ERROR) tokens.add(token);
                else return invalidTokenError(token.startPosition(), (String)token.value());
            }
            else return invalidTokenError(position, "Unknown Token");
        }

        tokens.add(new Token(TokenType.EOF, position, position + 1));
        return new LexerResult(tokens);
    }

    private LexerResult invalidTokenError(int start, String error)
    {
        position = start;
        currentChar = position < source.getSource().length() ? source.getSource().charAt(position) : null;

        StringBuilder unknownTokenBuilder = new StringBuilder();
        while (currentChar != null && !IGNORE_SET.contains(currentChar))
        {
            unknownTokenBuilder.append(currentChar);
            advance();
        }

        return new LexerResult(new MCLLexicalError(source.getCodeLocation(start), source.getCodeLocation(position), error, unknownTokenBuilder.toString()));
    }
}
