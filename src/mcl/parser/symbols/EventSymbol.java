package mcl.parser.symbols;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.AbstractCompilableSymbol;

import java.util.ArrayList;
import java.util.List;

public class EventSymbol extends AbstractCompilableSymbol
{
    public final String namespace;
    public final List<String> listenerFunctions;
    
    public EventSymbol(AbstractNode definition, String namespace,  String name)
    {
        super(definition, name);
        this.namespace = namespace;
        this.listenerFunctions = new ArrayList<>();
    }
}
