package mcl.compiler.exceptions;

import mcl.compiler.lexer.MCLLexer;

public class MCLLexicalException extends MCLException
{
    public MCLLexicalException(String token, MCLLexer lexer, int startPosition)
    {
        super(String.format("%s: Unknown MCL Token: %s", lexer.getSource().getCodeLocation(startPosition), token));
    }
}