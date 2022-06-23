package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class VariableDefinitionNode extends AbstractNode
{
    public final VariableSignatureNode signature;
    public final AbstractNode value;

    public VariableDefinitionNode(AbstractNode signature, AbstractNode valueNode)
    {
        super(signature.startPosition(), valueNode.endPosition());

        this.signature = (VariableSignatureNode)signature;
        this.value = valueNode;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = signature.createSymbols(compiler, source);
        if (error != null) return error;

        return value.createSymbols(compiler, source);
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.VOID;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("VAR_DEFINITION");

        signature.debugPrint(depth + 1);
        value.debugPrint(depth + 1);
    }
}
