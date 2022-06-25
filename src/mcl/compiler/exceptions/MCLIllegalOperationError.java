package mcl.compiler.exceptions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.parser.AbstractNode;

public class MCLIllegalOperationError extends MCLError
{
    public MCLIllegalOperationError(MCLCompiler compiler, AbstractNode node, String details)
    {
        super(compiler.getSource().getCodeLocation(node.startPosition()), compiler.getSource().getCodeLocation(node.endPosition()), "Illegal Operation", details);
    }
}
