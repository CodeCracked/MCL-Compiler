package mcl.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.IdentifierNode;

import java.util.function.Consumer;

public class EventDeclarationNode extends AbstractNode
{
    public final IdentifierNode identifier;
    
    public EventDeclarationNode(Token keyword, IdentifierNode identifier, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        this.identifier = identifier;
    }
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        consumer.accept(identifier);
        if (recursive) identifier.forEachChild(consumer, true);
    }
}
