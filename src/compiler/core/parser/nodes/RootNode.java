package compiler.core.parser.nodes;

import compiler.core.parser.AbstractNode;
import compiler.core.source.SourcePosition;

import java.util.List;

public class RootNode extends AbstractNode
{
    public final List<AbstractNode> sources;
    public final int failedSourceCount;
    
    public RootNode(SourcePosition start, SourcePosition end, List<AbstractNode> sources, int failedSourceCount)
    {
        super(start, end);
        this.sources = sources;
        this.failedSourceCount = failedSourceCount;
    }
    
    @Override
    public String toString()
    {
        return "RootNode[" + sources.size() + " successful sources, " + failedSourceCount + " failed sources]";
    }
}
