package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;

import java.util.function.Consumer;

public class EventDeclarationNode extends AbstractNode
{
    public final IdentifierNode identifier;
    public final ParameterListNode parameterList;
    
    public EventDeclarationNode(Token keyword, IdentifierNode identifier, ParameterListNode parameterList, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        this.identifier = identifier;
        this.parameterList = parameterList;
    }
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        consumer.accept(identifier);
        consumer.accept(parameterList);
        
        if (recursive)
        {
            identifier.forEachChild(consumer, true);
            parameterList.forEachChild(consumer, true);
        }
    }
}
