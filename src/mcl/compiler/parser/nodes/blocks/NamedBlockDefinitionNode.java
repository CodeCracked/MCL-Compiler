package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;

public abstract class NamedBlockDefinitionNode extends BlockDefinitionNode
{
    public final Token identifier;

    public NamedBlockDefinitionNode(int startPosition, int endPosition, Token identifier, AbstractNode body)
    {
        super(startPosition, endPosition, body, (String)identifier.value());
        this.identifier = identifier;
    }
}
