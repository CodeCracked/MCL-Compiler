package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class NumberNode extends AbstractNode
{
    public final Token<?> token;

    public NumberNode(Token<?> token)
    {
        this.token = token;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("    ".repeat(depth));
        System.out.println(token);
    }

    @Override
    public String toString()
    {
        return token.toString();
    }
}
