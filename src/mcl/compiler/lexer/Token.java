package mcl.compiler.lexer;

import java.util.Objects;

public class Token
{
    private TokenType tokenType;
    private String token;

    private String file;
    private int line;
    private int column;

    public Token(TokenType tokenType, String token, String file, int line, int column)
    {
        this.tokenType = tokenType;
        this.token = token;
        this.file = file;
        this.line = line;
        this.column = column;
    }

    public TokenType getTokenType()
    {
        return tokenType;
    }
    public String getToken()
    {
        return token;
    }
    public String getFile()
    {
        return file;
    }
    public int getLine()
    {
        return line;
    }
    public int getColumn()
    {
        return column;
    }
    public String getLocation()
    {
        return "(" + file + ", Line: " + line + ", Column: " + column + ")";
    }

    @Override
    public String toString()
    {
        return tokenType.isComplex() ? tokenType.name() + "(" + token + ")" : tokenType.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return line == token1.line && column == token1.column && tokenType == token1.tokenType && token.equals(token1.token) && file.equals(token1.file);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tokenType, token, file, line, column);
    }
}
