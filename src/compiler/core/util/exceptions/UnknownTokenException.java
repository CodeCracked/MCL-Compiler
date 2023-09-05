package compiler.core.util.exceptions;

import compiler.core.lexer.Token;

public class UnknownTokenException extends CompilerException
{
    public UnknownTokenException(Token token)
    {
        super(token.start(), "Unknown token '" + token.contents() + "'!");
    }
}
