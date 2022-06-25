package mcl.compiler.exceptions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.parser.AbstractNode;

public class MCLWrongTypeError extends MCLError
{
    public MCLWrongTypeError(MCLCompiler compiler, AbstractNode node, RuntimeType expected, RuntimeType found)
    {
        super(compiler.getSource().getCodeLocation(node.startPosition()), compiler.getSource().getCodeLocation(node.endPosition()), "Wrong Type", "Expected " + expected.getMinecraftName() + ", found " + found.getMinecraftName());
    }
}
