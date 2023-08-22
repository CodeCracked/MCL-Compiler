package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import mcl.parser.nodes.components.QualifiedIdentifierNode;

public class ListenerDeclarationNode extends AbstractNode
{
    public final QualifiedIdentifierNode event;
    public final ParameterListNode parameters;
    public final BlockNode body;
    
    public ListenerDeclarationNode(Token keyword, QualifiedIdentifierNode event, ParameterListNode parameters, BlockNode body)
    {
        super(keyword.start(), body.end());
        this.event = event;
        this.parameters = parameters;
        this.body = body;
    }
}
