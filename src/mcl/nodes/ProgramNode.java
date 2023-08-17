package mcl.nodes;

import compiler.core.parser.AbstractNode;

import java.util.List;
import java.util.function.Consumer;

public class ProgramNode extends AbstractNode
{
    public final List<NamespaceNode> namespaces;
    
    public ProgramNode(List<NamespaceNode> namespaces)
    {
        super(namespaces.get(0).start(), namespaces.get(namespaces.size() - 1).end());
        this.namespaces = namespaces;
    }
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        namespaces.forEach(consumer);
        if (recursive) namespaces.forEach(namespace -> namespace.forEachChild(consumer, true));
    }
}
