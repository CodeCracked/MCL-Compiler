package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.Collections;
import java.util.List;

public class ProgramRootNode extends AbstractNode
{
    public final List<AbstractNode> namespaces;

    public ProgramRootNode(List<AbstractNode> namespaces)
    {
        super(0, namespaces.size() > 0 ? namespaces.get(namespaces.size() - 1).endPosition() : 1);

        this.namespaces = Collections.unmodifiableList(namespaces);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (AbstractNode namespace : namespaces)
        {
            MCLError error = namespace.createSymbols(compiler, source);
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
        System.out.println("PROGRAM");
        for (AbstractNode namespace : namespaces) namespace.debugPrint(depth + 1);
    }
}
