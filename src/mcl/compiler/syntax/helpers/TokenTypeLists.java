package mcl.compiler.syntax.helpers;

import mcl.compiler.lexer.TokenType;

import java.util.List;

public final class TokenTypeLists
{
    public static final List<TokenType> MODIFIER_TOKEN_TYPES = List.of(TokenType.SAFE, TokenType.UNSAFE);
    public static final List<TokenType> TYPE_DECLARATION_TOKEN_TYPES = List.of(TokenType.INT, TokenType.FLOAT, TokenType.IDENTIFIER);
    public static final List<TokenType> VALUE_TOKEN_TYPES = List.of(TokenType.INT_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.IDENTIFIER);
}
