package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class BinOpNode extends AbstractNode
{
    private final AbstractNode leftNode;
    private final Token<?> operation;
    private final AbstractNode rightNode;

    public BinOpNode(AbstractNode leftNode, Token<?> operation, AbstractNode rightNode)
    {
        this.leftNode = leftNode;
        this.operation = operation;
        this.rightNode = rightNode;
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s, %s)", leftNode, operation, rightNode);
    }
}
