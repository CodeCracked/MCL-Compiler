package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class BinaryOpNode extends AbstractNode
{
    public final AbstractNode leftNode;
    public final Token<?> operation;
    public final AbstractNode rightNode;

    public BinaryOpNode(AbstractNode leftNode, Token<?> operation, AbstractNode rightNode)
    {
        this.leftNode = leftNode;
        this.operation = operation;
        this.rightNode = rightNode;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("    ".repeat(depth));
        System.out.println(operation);

        leftNode.debugPrint(depth + 1);
        rightNode.debugPrint(depth + 1);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s, %s)", leftNode, operation, rightNode);
    }
}
