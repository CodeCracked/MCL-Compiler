package mcl.compiler.lexer;

import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.exceptions.MCLLexicalError;

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
    private static Token<?> numberTokenBuilder(MCLLexer lexer, int startPosition)
    {
        StringBuilder numberBuilder = new StringBuilder();
        int dotCount = 0;

        while (lexer.getCurrentChar() != null && "0123456789.".indexOf(lexer.getCurrentChar()) != -1)
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
        if (numberString.length() == 0) return null;
        else if (dotCount == 0) return new Token<>(TokenType.INT, Integer.parseInt(numberString), startPosition, lexer.getPosition());
        else return new Token<>(TokenType.FLOAT, Float.parseFloat(numberString), startPosition, lexer.getPosition());
    }
    //endregion

    TokenType() { }
    TokenType(char symbol)
    {
        MCLLexer.registerTokenBuilder((lexer, startPosition) -> symbolTokenBuilder(lexer, startPosition, symbol));
    }

    //region Token Builders
    private Token<Void> symbolTokenBuilder(MCLLexer lexer, int startPosition, char symbol)
    {
        if (symbol == lexer.getCurrentChar())
        {
            lexer.advance();
            return new Token<>(this, startPosition, lexer.getPosition());
        }
        else return null;
    }
    //endregion
}
