package mcl.compiler.lexer;

import mcl.compiler.MCLKeywords;
import mcl.compiler.source.MCLSourceCollection;

public enum TokenType
{
    INTERNAL_ERROR,

    INT,
    FLOAT,
    IDENTIFIER,
    KEYWORD,
    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    EQUALS('='),
    LPAREN('('),
    RPAREN(')'),
    NEWLINE(MCLSourceCollection.LINE_SEPARATOR),
    EOF
    ;

    //region Static
    private static final String DIGITS = "012345789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String IDENTIFIER_CHARACTERS = LETTERS + DIGITS + "_";

    public static void registerTokenBuilders()
    {
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
        if (lexer.getCurrentChar() != null && IDENTIFIER_CHARACTERS.indexOf(lexer.getCurrentChar()) != -1) return new Token(TokenType.INTERNAL_ERROR, startPosition, lexer.getPosition());

        String numberString = numberBuilder.toString().trim();
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

    TokenType() { }
    TokenType(char symbol)
    {
        MCLLexer.registerTokenBuilder((lexer, startPosition) -> symbolTokenBuilder(lexer, startPosition, symbol));
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
    //endregion
}
