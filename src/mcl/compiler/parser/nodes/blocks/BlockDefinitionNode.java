package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class BlockDefinitionNode extends AbstractNode
{
    public final Object symbolTableID;
    public final AbstractNode body;
    public final String blockName;

    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body, String blockName)
    {
        this(startPosition, endPosition, body, blockName, UUID.randomUUID());
    }
    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body, String blockName, Object symbolTableID)
    {
        super(startPosition, endPosition);

        this.symbolTableID = symbolTableID;
        this.body = body;
        this.blockName = blockName;
    }

    protected Path getDefinitionFolder(Path target) { return target; }
    protected void walkChildren(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) {  }
    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source) { return null; }
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source) { return null; }

    @Override
    public final void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, body);
        body.walk(parentChildConsumer);
    }

    @Override
    public final MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        compiler.pushSymbolTable(symbolTableID);
        MCLError error = createContextSymbols(compiler, source);
        compiler.popSymbolTable();
        if (error != null) return error;

        error = createDefinitionSymbol(compiler, source);
        if (error != null) return error;

        compiler.pushSymbolTable(symbolTableID);
        error = body.createSymbols(compiler, source);
        compiler.popSymbolTable();

        return error;
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error;

        compiler.pushSymbolTable(symbolTableID);
        error = body.symbolAnalysis(compiler, source);
        compiler.popSymbolTable();

        return error;
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = getDefinitionFolder(target);
        transpiler.createDirectory(transpileTarget);
        body.setTranspileTarget(transpiler, transpileTarget);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        transpiler.getCompiler().pushSymbolTable(symbolTableID);
        MCLError error = body.transpile(transpiler);
        transpiler.getCompiler().popSymbolTable();

        return error;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }
}
