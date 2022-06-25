package mcl.compiler.parser;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public abstract class AbstractNode
{
    public AbstractNode parent;

    private final int startPosition;
    private final int endPosition;

    protected Path transpileTarget;

    public AbstractNode(int startPosition, int endPosition)
    {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public abstract void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer);
    public abstract MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source);
    public abstract MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source);
    public abstract void setTranspileTarget(MCLCompiler compiler, Path target) throws IOException;
    public abstract MCLError transpile(MCLTranspiler transpiler) throws IOException;

    public abstract RuntimeType getRuntimeType(MCLCompiler compiler);
    public abstract void debugPrint(int depth);

    public int startPosition() { return this.startPosition; }
    public int endPosition() { return this.endPosition; }
    public Path transpileTarget() { return this.transpileTarget; }
}
