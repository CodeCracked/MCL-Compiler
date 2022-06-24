package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.UUID;

public abstract class BlockDefinitionNode extends AbstractNode
{
    public final UUID symbolTableID;
    public final AbstractNode body;

    public BlockDefinitionNode(int startPosition, int endPosition, AbstractNode body)
    {
        super(startPosition, endPosition);

        this.symbolTableID = UUID.randomUUID();
        this.body = body;
    }

    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source) { return null; }
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source) { return null; }

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
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }
}
