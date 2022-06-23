package mcl.compiler.lexer;

public interface TokenBuilder
{
    Token buildToken(MCLLexer lexer, int startPosition);
}
