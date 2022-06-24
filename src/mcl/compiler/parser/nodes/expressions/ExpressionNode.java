package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.parser.AbstractNode;

public abstract class ExpressionNode extends AbstractNode
{
    public ExpressionNode(int startPosition, int endPosition)
    {
        super(startPosition, endPosition);
    }

    public abstract ExpressionNode simplify();
}
