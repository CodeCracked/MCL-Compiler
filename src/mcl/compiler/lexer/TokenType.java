package mcl.compiler.lexer;

import mcl.compiler.MCLKeywords;
import mcl.compiler.source.MCLSourceCollection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum TokenType
{
    INTERNAL_ERROR,

    INT,
    FLOAT,
    IDENTIFIER,
    KEYWORD,

    PLUS(2, '+'),
    MINUS(2, '-'),
    MUL(2, '*'),
    DIV(2, '/'),
    ASSIGN(2, '='),

    EQUALS(1, "=="),
    NOT_EQUALS(1, "!="),
    LESS(2, '<'),
    GREATER(2, '>'),
    LESS_OR_EQUAL(1, "<="),
    GREATER_OR_EQUAL(1, ">="),

    LPAREN(2, '('),
    RPAREN(2, ')'),
    NEWLINE(0, MCLSourceCollection.LINE_SEPARATOR),
    EOF
    ;

    //region Static
    private static final String DIGITS = "012345789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String IDENTIFIER_CHARACTERS = LETTERS + DIGITS + "_";

    public static void registerTokenBuilders()
    {
        List<TokenType> types = Arrays.asList(TokenType.values());
        types.sort(Comparator.comparingInt(a -> a.priority));
        for (TokenType type : types) if (type.tokenBuilder != null) MCLLexer.registerTokenBuilder(type.tokenBuilder);

        MCLLexer.registerTokenBuilder(TokenType::numberTokenBuilder);
        MCLLexer.registerTokenBuilder(TokenType::textTokenBuilder);
    }

    private static Token numberTokenBuilder(MCLLexer lexer, int startPosition)
    {
        StringBuilder numberBuilder = new StringBuilder();
        int dotCount = 0;

        while (lexer.getCurrentChar() != null && (DIGITS + ".").indexOf(lexer.getCurrentChar()) != -1)
        {
            if (lexer.getCurrentChar() == '.')
            {
                dotCount++;
                if (dotCount > 1) break;
            }
            numberBuilder.append(lexer.getCurrentChar());
            lexer.advance();
        }
        String numberString = numberBuilder.toString().trim();

        if (numberString.length() > 0 && lexer.getCurrentChar() != null && IDENTIFIER_CHARACTERS.indexOf(lexer.getCurrentChar()) != -1) return new Token(TokenType.INTERNAL_ERROR, "Invalid Number", startPosition, lexer.getPosition());

        if (numberString.length() == 0) return null;
        else if (dotCount == 0) return new Token(TokenType.INT, Integer.parseInt(numberString), startPosition, lexer.getPosition());
        else return new Token(TokenType.FLOAT, Float.parseFloat(numberString), startPosition, lexer.getPosition());
    }
    private static Token textTokenBuilder(MCLLexer lexer, int startPosition)
    {
        StringBuilder textBuilder = new StringBuilder();

        if (lexer.getCurrentChar() != null && LETTERS.indexOf(lexer.getCurrentChar()) != -1)
        {
            textBuilder.append(lexer.getCurrentChar());
            lexer.advance();
        }
        else return null;

        while (lexer.getCurrentChar() != null && IDENTIFIER_CHARACTERS.indexOf(lexer.getCurrentChar()) != -1)
        {
            textBuilder.append(lexer.getCurrentChar());
            lexer.advance();
        }

        String text = textBuilder.toString();
        if (MCLKeywords.KEYWORDS.contains(text)) return new Token(TokenType.KEYWORD, text, startPosition, lexer.getPosition());
        else return new Token(TokenType.IDENTIFIER, text, startPosition, lexer.getPosition());
    }
    //endregion

    final int priority;
    final TokenBuilder tokenBuilder;

    TokenType() { this.priority = 0; this.tokenBuilder = null; }
    TokenType(int priority, char symbol)
    {
        this.priority = priority;
        this.tokenBuilder = (lexer, startPosition) -> symbolTokenBuilder(lexer, startPosition, symbol);
    }
    TokenType(int priority, String symbol)
    {
        this.priority = priority;
        this.tokenBuilder = (lexer, startPosition) -> symbolTokenBuilder(lexer, startPosition, symbol);
    }

    //region Token Builders
    private Token symbolTokenBuilder(MCLLexer lexer, int startPosition, char symbol)
    {
        if (symbol == lexer.getCurrentChar())
        {
            lexer.advance();
            return new Token(this, startPosition, lexer.getPosition());
        }
        else return null;
    }
    private Token symbolTokenBuilder(MCLLexer lexer, int startPosition, String symbol)
    {
        char[] symbolChars = symbol.toCharArray();
        for (char c : symbolChars)
        {
            if (c == lexer.getCurrentChar()) lexer.advance();
            else
            {
                lexer.setPosition(startPosition);
                return null;
            }
        }
        return new Token(this, startPosition, lexer.getPosition());
    }
    //endregion
}
