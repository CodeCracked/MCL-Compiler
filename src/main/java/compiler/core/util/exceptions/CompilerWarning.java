package compiler.core.util.exceptions;

import compiler.core.parser.AbstractNode;
import compiler.core.source.SourcePosition;

public class CompilerWarning extends CompilerException
{
    public CompilerWarning(AbstractNode node, String message)
    {
        super(node.start(), node.end(), message);
    }
    public CompilerWarning(SourcePosition start, SourcePosition end, String message)
    {
        super(start, end, message);
    }
}
