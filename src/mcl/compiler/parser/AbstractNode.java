package mcl.compiler.parser;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.source.MCLSourceCollection;

public abstract class AbstractNode
{
    private final int startPosition;
    private final int endPosition;

    public AbstractNode(int startPosition, int endPosition)
    {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public abstract MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source);
    public abstract void debugPrint(int depth);

    public int startPosition() { return this.startPosition; }
    public int endPosition() { return this.endPosition; }
}
