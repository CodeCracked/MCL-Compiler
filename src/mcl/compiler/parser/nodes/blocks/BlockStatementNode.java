package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class BlockStatementNode extends AbstractNode
{
    public final List<AbstractNode> statements;

    public BlockStatementNode(List<AbstractNode> statements, int start)
    {
        super(start, statements.size() > 0 ? statements.get(statements.size() - 1).endPosition() : start + 1);
        this.statements = Collections.unmodifiableList(statements);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (AbstractNode statement : statements)
        {
            parentChildConsumer.accept(this, statement);
            statement.walk(parentChildConsumer);
        }
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        for (AbstractNode statement : statements)
        {
            MCLError error = statement.transpile(transpiler, target);
            if (error != null) return error;
        }
        return null;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (AbstractNode statement : statements)
        {
            MCLError error = statement.createSymbols(compiler, source);
            if (error != null) return error;
        }
        return null;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("BLOCK");

        for (AbstractNode statement : statements) statement.debugPrint(depth + 1);
    }
}
