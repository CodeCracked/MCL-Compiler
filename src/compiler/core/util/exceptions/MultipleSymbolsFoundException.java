package compiler.core.util.exceptions;

import compiler.core.parser.AbstractNode;

public class MultipleSymbolsFoundException extends CompilerException
{
    public MultipleSymbolsFoundException(AbstractNode source, String symbolType)
    {
        super(source.start(), source.end(), "Multiple matching " + symbolType + "s found!");
    }
}
