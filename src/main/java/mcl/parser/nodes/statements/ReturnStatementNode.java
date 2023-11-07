package mcl.parser.nodes.statements;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;

public class ReturnStatementNode extends AbstractNode
{
    public final AbstractValueNode expression;
    
    public ReturnStatementNode(Token keyword, AbstractValueNode expression, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        this.expression = expression;
    }
}
