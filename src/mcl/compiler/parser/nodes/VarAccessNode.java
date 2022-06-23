package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class VarAccessNode extends AbstractNode
{
    private final Token identifier;

    public VarAccessNode(Token identifier)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("VARIABLE:" + identifier.value());
    }
}
