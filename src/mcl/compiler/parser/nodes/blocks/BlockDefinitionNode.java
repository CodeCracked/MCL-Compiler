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
    public final AbstractNode[] blocks;
    public final String blockName;

    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body, String blockName)
    {
        this(startPosition, endPosition, blockName, UUID.randomUUID(), body);
    }
    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body, String blockName, Object symbolTableID)
    {
        this(startPosition, endPosition, blockName, symbolTableID, body);
    }
    public BlockDefinitionNode(int startPosition, int endPosition, String blockName, AbstractNode... blocks)
    {
        this(startPosition, endPosition, blockName, UUID.randomUUID(), blocks);
    }
    public BlockDefinitionNode(int startPosition, int endPosition, String blockName, Object symbolTableID, AbstractNode... blocks)
    {
        super(startPosition, endPosition);

        this.symbolTableID = symbolTableID;
        this.blocks = blocks;
        this.blockName = blockName;
    }

    protected Path getDefinitionFolder(Path target) { return target; }
    protected Path getBlockDefinitionFolder(AbstractNode block, Path target) { return target; }
    protected MCLError transpileStatement(MCLTranspiler transpiler, Path target) { return null; }

    protected void walkChildren(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) {  }
    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source) { return null; }
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source) { return null; }

    @Override
    public final void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (AbstractNode block : blocks)
        {
            parentChildConsumer.accept(this, block);
            block.walk(parentChildConsumer);
        }
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
        for (AbstractNode block : blocks)
        {
            error = block.createSymbols(compiler, source);
            if (error != null) return error;
        }
        compiler.popSymbolTable();

        return null;
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        compiler.pushSymbolTable(symbolTableID);
        for (AbstractNode block : blocks)
        {
            MCLError error = block.symbolAnalysis(compiler, source);
            if (error != null) return error;
        }
        compiler.popSymbolTable();

        return null;
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = getDefinitionFolder(target);
        transpiler.createDirectory(transpileTarget);
        for (AbstractNode block : blocks) block.setTranspileTarget(transpiler, getBlockDefinitionFolder(block, transpileTarget));
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        transpiler.getCompiler().pushSymbolTable(symbolTableID);
        for (AbstractNode block : blocks)
        {
            MCLError error = block.transpile(transpiler);
            if (error != null) return error;
        }
        transpiler.getCompiler().popSymbolTable();

        return null;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }
}
