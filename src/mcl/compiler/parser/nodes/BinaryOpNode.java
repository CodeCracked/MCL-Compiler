package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class BinaryOpNode extends AbstractNode
{
    public final AbstractNode leftNode;
    public final Token operation;
    public final AbstractNode rightNode;

    public BinaryOpNode(AbstractNode leftNode, Token operation, AbstractNode rightNode)
    {
        super(leftNode.startPosition(), rightNode.endPosition());

        this.leftNode = leftNode;
        this.operation = operation;
        this.rightNode = rightNode;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = leftNode.createSymbols(compiler, source);
        if (error != null) return error;

        error = rightNode.createSymbols(compiler, source);
        return error;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
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
