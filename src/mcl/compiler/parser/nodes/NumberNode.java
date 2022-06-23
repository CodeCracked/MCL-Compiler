package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class NumberNode extends AbstractNode
{
    public final Token token;

    public NumberNode(Token token)
    {
        super(token.startPosition(), token.endPosition());
        this.token = token;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println(token);
    }

    @Override
    public String toString()
    {
        return token.toString();
    }
}
