package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class VarAssignNode extends AbstractNode
{
    public final Token keyword;
    public final Token identifier;
    public final AbstractNode value;

    public VarAssignNode(Token keyword, Token identifier, AbstractNode valueNode)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.keyword = keyword;
        this.identifier = identifier;
        this.value = valueNode;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("ASSIGN:" + keyword.value());

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        value.debugPrint(depth + 1);
    }
}
