package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class UnaryOpNode extends AbstractNode
{
    public final Token<?> operation;
    public final AbstractNode node;

    public UnaryOpNode(Token<?> operation, AbstractNode node)
    {
        this.operation = operation;
        this.node = node;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("    ".repeat(depth));
        System.out.println(operation);

        node.debugPrint(depth + 1);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s)", operation, node);
    }
}
