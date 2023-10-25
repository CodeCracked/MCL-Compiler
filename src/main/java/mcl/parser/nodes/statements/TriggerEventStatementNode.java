package mcl.parser.nodes.statements;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.ArgumentListNode;
import mcl.parser.nodes.components.QualifiedIdentifierNode;

public class TriggerEventStatementNode extends AbstractNode
{
    public final QualifiedIdentifierNode event;
    public final ArgumentListNode arguments;
    
    public TriggerEventStatementNode(Token keyword, QualifiedIdentifierNode event, ArgumentListNode arguments, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        this.event = event;
        this.arguments = arguments;
    }
}
