package mcl.parser.symbols;

import compiler.core.codegen.ICompilable;
import compiler.core.parser.symbols.AbstractSymbol;
import mcl.parser.nodes.declarations.EventDeclarationNode;

import java.util.ArrayList;
import java.util.List;

public class EventSymbol extends AbstractSymbol<EventDeclarationNode> implements ICompilable
{
    public final String namespace;
    public final List<String> listenerFunctions;
    
    public EventSymbol(EventDeclarationNode definition, String namespace, String name)
    {
        super(definition, name);
        this.namespace = namespace;
        this.listenerFunctions = new ArrayList<>();
    }
}
