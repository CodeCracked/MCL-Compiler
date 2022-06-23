package mcl.compiler.parser.nodes;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public class NumberNode extends AbstractNode
{
    private final Token<?> token;

    public NumberNode(Token<?> token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return token.toString();
    }
}
