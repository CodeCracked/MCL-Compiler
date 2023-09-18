package mcl.parser.symbols;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.AbstractSymbol;

public class EventSymbol extends AbstractSymbol
{
    public EventSymbol(AbstractNode definition, String name)
    {
        super(definition, name);
    }
}
