package compiler.core.util.exceptions;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.AbstractSymbol;

public class DuplicateSymbolException extends CompilerException
{
    public DuplicateSymbolException(AbstractNode symbolDefinition, AbstractSymbol failedSymbol)
    {
        super(symbolDefinition.start(), symbolDefinition.end(), "Duplicate " + failedSymbol.getClass().getSimpleName().replace("Symbol", "").toLowerCase().trim() + " '" + failedSymbol.name() + "'!");
    }
}
