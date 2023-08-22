package compiler.core.parser.nodes.components;

import compiler.core.parser.AbstractNode;
import compiler.core.source.SourcePosition;

import java.util.List;
import java.util.function.Consumer;

public class BlockNode extends AbstractNode
{
    public final List<AbstractNode> children;
    
    public BlockNode(SourcePosition start, SourcePosition end, List<AbstractNode> children)
    {
        super(start, end);
        this.children = children;
    }
}
