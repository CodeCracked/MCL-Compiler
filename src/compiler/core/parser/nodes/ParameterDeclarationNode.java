package compiler.core.parser.nodes;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.DataType;
import compiler.core.parser.AbstractNode;

import java.util.function.Consumer;

public class ParameterDeclarationNode extends AbstractNode
{
    public final DataType dataType;
    public final IdentifierNode identifier;
    
    public ParameterDeclarationNode(Token typeToken, DataType dataType, IdentifierNode identifier)
    {
        super(typeToken.start(), identifier.end());
        this.dataType = dataType;
        this.identifier = identifier;
    }
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        consumer.accept(identifier);
        if (recursive) identifier.forEachChild(consumer, true);
    }
}
