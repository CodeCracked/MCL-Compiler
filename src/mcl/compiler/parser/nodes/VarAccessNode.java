package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class VarAccessNode extends AbstractNode
{
    private final Token identifier;

    public VarAccessNode(Token identifier)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
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
        System.out.println("VARIABLE:" + identifier.value());
    }
}
