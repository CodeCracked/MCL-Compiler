package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.source.CodeSource;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.exceptions.CompilerWarning;
import compiler.core.util.exceptions.UndefinedSymbolException;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.symbols.EventSymbol;
import mcl.util.Salt;
import mcl.util.Validations;

import java.util.Optional;

public class ListenerDeclarationNode extends AbstractNode
{
    public final QualifiedIdentifierNode event;
    public final ParameterListNode parameters;
    public final BlockNode body;
    
    private SymbolTable childTable;
    private SymbolTable.SymbolEntry<EventSymbol> eventSymbol;
    private String functionName;
    
    public ListenerDeclarationNode(Token keyword, QualifiedIdentifierNode event, ParameterListNode parameters, BlockNode body)
    {
        super(keyword.start(), body.end());
        this.event = event;
        this.parameters = parameters;
        this.body = body;
    }
    
    public EventSymbol eventSymbol() { return eventSymbol.symbol(); }
    public String functionName() { return functionName; }
    
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
        
        // Warn about minecraft:load
        if (event.namespace.value.equals("minecraft") && event.identifier.value.equals("load"))
        {
            if (!(start().getSource() instanceof CodeSource.Resource resource && resource.resourceName().startsWith("stdlib")))
            {
                result.addWarning(new CompilerWarning(event.start(), parameters.end(), "MCL code inside a 'minecraft:load' event listener may run before MCL has finished installation. Consider using 'mcl:reload' instead."));
            }
        }
        
        // Register Listener
        NamespaceNode namespace = result.register(findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        this.functionName = event.namespace.value + "_" + event.identifier.value + "_" + Salt.newSalt(4);
        this.eventSymbol.symbol().listenerFunctions.add(namespace.identifier.value + ":listeners/" + functionName);
        
        return result.success(null);
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        return parameters.createParameterSymbols();
    }
    
    @Override
    protected Result<Void> validate()
    {
        if (Validations.ensureParameterListsMatch(eventSymbol.symbol().definition().parameterList, parameters)) return Result.of(null);
        else
        {
            String errorBuilder = "Cannot find event '" + eventSymbol.symbol().namespace + ':' + eventSymbol.symbol().name() + "' with matching parameter types!";
            return Result.fail(new CompilerException(parameters.start(), parameters.end(), errorBuilder));
        }
    }
}
