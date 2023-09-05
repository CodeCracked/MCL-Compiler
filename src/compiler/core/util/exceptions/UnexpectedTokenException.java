package compiler.core.util.exceptions;

import compiler.core.parser.Parser;

public class UnexpectedTokenException extends CompilerException
{
    private UnexpectedTokenException(Parser parser, String error)
    {
        super(parser.getCurrentToken().start(), error);
    }
    
    public static UnexpectedTokenException expected(Parser parser, String expected)
    {
        return new UnexpectedTokenException(parser, "Unexpected token '" + parser.getCurrentToken().contents() + "'! Expected " + expected);
    }
    public static UnexpectedTokenException explicit(Parser parser, String error)
    {
        return new UnexpectedTokenException(parser, error);
    }
}
