package mcl.parser.nodes;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.IdentifierNode;

import java.util.function.Consumer;

public class NamespaceNode extends AbstractNode
{
    public final IdentifierNode identifier;
    public final BlockNode body;
    
    public NamespaceNode(Token keyword, IdentifierNode identifier, BlockNode body)
    {
        super(keyword.start(), body.end());
        
        this.identifier = identifier;
        this.body = body;
    }
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        consumer.accept(identifier);
        consumer.accept(body);
        
        if (recursive)
        {
            identifier.forEachChild(consumer, true);
            body.forEachChild(consumer, true);
        }
    }
}
