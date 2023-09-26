package compiler.core.parser.symbols;

import compiler.core.parser.AbstractNode;

public abstract class AbstractCompilableSymbol extends AbstractSymbol
{
    public AbstractCompilableSymbol(AbstractNode definition, String name)
    {
        super(definition, name);
    }
}
