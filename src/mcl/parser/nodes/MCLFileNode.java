package mcl.parser.nodes;

import compiler.core.parser.AbstractNode;

import java.util.List;

public class MCLFileNode extends AbstractNode
{
    public final List<NamespaceNode> namespaces;
    
    public MCLFileNode(List<NamespaceNode> namespaces)
    {
        super(namespaces.get(0).start(), namespaces.get(namespaces.size() - 1).end());
        this.namespaces = namespaces;
    }
}
