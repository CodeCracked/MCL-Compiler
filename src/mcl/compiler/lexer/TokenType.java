package mcl.compiler.lexer;

import mcl.compiler.MCLSourceCollection;
import mcl.compiler.exceptions.MCLLexicalException;

public enum TokenType
{
    INT,
    FLOAT,
    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    LPAREN('('),
    RPAREN(')'),
    END_OF_LINE(MCLSourceCollection.LINE_SEPARATOR)
    ;

    //region Static
    public static void registerTokenBuilders()
    {
        MCLLexer.registerTokenBuilder(TokenType::numberTokenBuilder);
    }
    private static Token<?> numberTokenBuilder(MCLLexer lexer, int startPosition) throws MCLLexicalException
    {
        StringBuilder numberBuilder = new StringBuilder();
        int dotCount = 0;

        while (lexer.getCurrentChar() != null && "0123456789.".indexOf(lexer.getCurrentChar()) != -1)
        {
            if (lexer.getCurrentChar() == '.') dotCount++;
            numberBuilder.append(lexer.getCurrentChar());
            lexer.advance();
        }

        String numberString = numberBuilder.toString().trim();
        if (numberString.length() == 0) return null;
        else if (dotCount == 0) return new Token<>(TokenType.INT, Integer.parseInt(numberString));
        else if (dotCount == 1) return new Token<>(TokenType.FLOAT, Float.parseFloat(numberString));
        else throw new MCLLexicalException(numberString, lexer, startPosition);
    }
    //endregion

    TokenType() { }
    TokenType(char symbol)
    {
        MCLLexer.registerTokenBuilder((lexer, startPosition) -> symbolTokenBuilder(lexer, symbol));
    }

    //region Token Builders
    private Token<Void> symbolTokenBuilder(MCLLexer lexer, char symbol)
    {
        if (symbol == lexer.getCurrentChar())
        {
            lexer.advance();
            return new Token<>(this);
        }
        else return null;
    }
    //endregion
}
