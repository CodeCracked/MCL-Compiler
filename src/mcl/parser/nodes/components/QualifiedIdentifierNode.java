package mcl.parser.nodes.components;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.util.Result;
import compiler.core.util.annotations.OptionalChild;
import mcl.parser.nodes.NamespaceNode;

public class QualifiedIdentifierNode extends AbstractNode
{
    @OptionalChild public IdentifierNode namespace;
    public final IdentifierNode identifier;
    
    public QualifiedIdentifierNode(IdentifierNode identifier) { this(null, identifier); }
    public QualifiedIdentifierNode(IdentifierNode namespace, IdentifierNode identifier)
    {
        super(namespace != null ? namespace.start() : identifier.start(), identifier.end());
        this.namespace = namespace;
        this.identifier = identifier;
    }
    
    @Override
    protected Result<Void> populate()
    {
        Result<Void> result = new Result<>();
        
        // Populate Default Namespace
        if (namespace == null)
        {
            NamespaceNode inNamespace = result.register(findParentNode(NamespaceNode.class));
            if (result.getFailure() != null) return result;
            
            namespace = new IdentifierNode(identifier.start(), identifier.start(), inNamespace.identifier.value);
        }
        
        return result.success(null);
    }
}
