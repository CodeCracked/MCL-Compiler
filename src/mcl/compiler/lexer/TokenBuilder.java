package mcl.compiler.lexer;

import mcl.compiler.exceptions.MCLLexicalException;

public interface TokenBuilder
{
    Token<?> buildToken(MCLLexer lexer, int startPosition) throws MCLLexicalException;
}
