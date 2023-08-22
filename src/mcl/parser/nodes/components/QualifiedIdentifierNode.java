package mcl.parser.nodes.components;

import compiler.core.annotations.OptionalChild;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.IdentifierNode;

public class QualifiedIdentifierNode extends AbstractNode
{
    @OptionalChild public final IdentifierNode namespace;
    public final IdentifierNode identifier;
    
    public QualifiedIdentifierNode(IdentifierNode identifier) { this(null, identifier); }
    public QualifiedIdentifierNode(IdentifierNode namespace, IdentifierNode identifier)
    {
        super(namespace != null ? namespace.start() : identifier.start(), identifier.end());
        this.namespace = namespace;
        this.identifier = identifier;
    }
}
