package compiler.core.util.exceptions;

import compiler.core.parser.AbstractNode;

public class UndefinedSymbolException extends CompilerException
{
    public UndefinedSymbolException(AbstractNode source, String symbolType)
    {
        this(source, symbolType, null);
    }
    public UndefinedSymbolException(AbstractNode source, String symbolType, String description)
    {
        super(source.start(), source.end(), "Unknown " + symbolType + (description != null ? description : "") + "!");
    }
}
