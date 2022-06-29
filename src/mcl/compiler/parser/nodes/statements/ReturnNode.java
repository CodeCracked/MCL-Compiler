package mcl.compiler.parser.nodes.statements;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class ReturnNode extends AbstractNode
{
    public final AbstractNode value;

    public ReturnNode(Token keyword, AbstractNode value)
    {
        super(keyword.startPosition(), value.endPosition());
        this.value = value;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, value);
        value.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return value.createSymbols(compiler, source);
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        return value.symbolAnalysis(compiler, source);
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        value.setTranspileTarget(transpiler, target);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        MCLError error = transpiler.comment(transpileTarget, "RETURN_VALUE");
        if (error != null) return error;

        error = value.transpile(transpiler);
        if (error != null) return error;

        error = transpiler.assignReturn(transpileTarget, getRuntimeType(transpiler.getCompiler()), 0);
        if (error != null) return error;

        error = transpiler.comment(transpileTarget, "END RETURN_VALUE");
        return error;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return value.getRuntimeType(compiler);
    }
    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("RETURN");
        value.debugPrint(depth + 1);
    }
}
