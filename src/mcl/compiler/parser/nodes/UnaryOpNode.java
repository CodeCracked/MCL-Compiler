package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class UnaryOpNode extends AbstractNode
{
    public final Token operation;
    public final AbstractNode node;

    public UnaryOpNode(Token operation, AbstractNode node)
    {
        super(operation.startPosition(), node.endPosition());

        this.operation = operation;
        this.node = node;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return node.createSymbols(compiler, source);
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return node.getRuntimeType(compiler);
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println(operation);

        node.debugPrint(depth + 1);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s)", operation, node);
    }
}
