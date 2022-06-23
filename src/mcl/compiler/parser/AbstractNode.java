package mcl.compiler.parser;

public abstract class AbstractNode
{
    private final int startPosition;
    private final int endPosition;

    public AbstractNode(int startPosition, int endPosition)
    {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public abstract void debugPrint(int depth);

    public int startPosition() { return this.startPosition; }
    public int endPosition() { return this.endPosition; }
}
