package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;

import java.util.Collection;

public class MCLSyntaxException extends MCLException
{
    public MCLSyntaxException(Token at, String message)
    {
        super(at.getLocation() + " " + message);
    }
    public MCLSyntaxException(TokenType expected, Token found)
    {
        super(found.getLocation() + " Unexpected symbol '" + found.getToken().replace("\n", "\\n") + "'! Expected " + expected.getPrintableName());
    }
    public MCLSyntaxException(Collection<TokenType> expected, Token found)
    {
        super(found.getLocation() + " Unexpected symbol '" + found.getToken().replace("\n", "\\n") + "'! Expected " + merge(expected));
    }

    private static String merge(Collection<TokenType> expected)
    {
        int size = expected.size();
        if (size == 1)
        {
            for (TokenType type : expected)
            {
                return type.getPrintableName();
            }
        }
        else if (size == 2)
        {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (TokenType type : expected)
            {
                if (i == 1) sb.append(" or ");
                sb.append(type.getPrintableName());
                i++;
            }
            return sb.toString();
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (TokenType type : expected)
        {
            if (i == size - 1) sb.append("or ");
            sb.append(type.getPrintableName());
            sb.append(", ");
            i++;
        }
        return sb.toString();
    }
}
