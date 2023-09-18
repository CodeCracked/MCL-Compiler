package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UndefinedSymbolException;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.symbols.EventSymbol;

import java.util.Optional;

public class ListenerDeclarationNode extends AbstractNode
{
    public final QualifiedIdentifierNode event;
    public final ParameterListNode parameters;
    public final BlockNode body;
    
    private SymbolTable childTable;
    private SymbolTable.SymbolEntry<EventSymbol> eventSymbol;
    
    public ListenerDeclarationNode(Token keyword, QualifiedIdentifierNode event, ParameterListNode parameters, BlockNode body)
    {
        super(keyword.start(), body.end());
        this.event = event;
        this.parameters = parameters;
        this.body = body;
    }
    
    public EventSymbol eventSymbol() { return eventSymbol.symbol(); }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Listener " + event.namespace.value + ":" + event.identifier.value);
        return childTable;
    }
    
    @Override
    protected Result<Void> retrieveSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Get Namespace Table
        Optional<SymbolTable> namespaceTable = symbolTable().root().tryGetChildTable(event.namespace.value);
        if (namespaceTable.isEmpty()) return result.failure(new UndefinedSymbolException(event.namespace, "namespace", " '" + event.namespace.value + "'"));
        
        // Get Event Symbol
        this.eventSymbol = result.register(namespaceTable.get().lookupByName(event.identifier, EventSymbol.class, event.identifier.value).single());
        if (result.getFailure() != null) return result;
        
        return result.success(null);
    }
}
