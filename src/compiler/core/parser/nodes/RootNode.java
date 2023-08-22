package compiler.core.parser.nodes;

import compiler.core.parser.AbstractNode;

import java.util.List;

public class RootNode extends AbstractNode
{
    public final List<AbstractNode> files;
    public final int failedSourceCount;
    
    public RootNode(List<AbstractNode> files, int failedSourceCount)
    {
        super(files.get(0).start(), files.get(files.size() - 1).end());
        this.files = files;
        this.failedSourceCount = failedSourceCount;
    }
    
    @Override
    public String toString()
    {
        return "RootNode[" + files.size() + " successful sources, " + failedSourceCount + " failed sources]";
    }
}
