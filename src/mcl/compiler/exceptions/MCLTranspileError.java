package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.CodeLocation;
import mcl.compiler.source.MCLSourceCollection;

public class MCLTranspileError extends MCLError
{
    public MCLTranspileError(MCLSourceCollection source, Token token, String details)
    {
        this(source.getCodeLocation(token.startPosition()), source.getCodeLocation(token.endPosition()), details);
    }
    public MCLTranspileError(MCLSourceCollection source, AbstractNode node, String details)
    {
        this(source.getCodeLocation(node.startPosition()), source.getCodeLocation(node.endPosition()), details);
    }
    public MCLTranspileError(CodeLocation start, CodeLocation end, String details)
    {
        super(start, end, "Transpile Error", details);
    }
}