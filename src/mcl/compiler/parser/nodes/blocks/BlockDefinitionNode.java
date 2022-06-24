package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class BlockDefinitionNode extends AbstractNode
{
    public final UUID symbolTableID;
    public final AbstractNode body;
    public final String blockType;

    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body, String blockName)
    {
        super(startPosition, endPosition);

        this.symbolTableID = UUID.randomUUID();
        this.body = body;
        this.blockType = blockName;
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
        MCLError error = createDefinitionSymbol(compiler, source);
        if (error != null) return error;

        compiler.pushSymbolTable(symbolTableID);
        {
            error = createContextSymbols(compiler, source);
            if (error != null) return error;

            error = body.createSymbols(compiler, source);
            if (error != null) return error;
        }
        compiler.popSymbolTable();

        return null;
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        Path definitionFolder = getDefinitionFolder(target);

        transpiler.getCompiler().pushSymbolTable(symbolTableID);
        MCLError error = body.transpile(transpiler, definitionFolder);
        transpiler.getCompiler().popSymbolTable();

        return error;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }
}
