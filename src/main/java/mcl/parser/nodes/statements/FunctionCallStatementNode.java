package mcl.parser.nodes.statements;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import mcl.parser.nodes.components.FunctionCallNode;

public class FunctionCallStatementNode extends AbstractNode
{
    public final FunctionCallNode call;
    
    public FunctionCallStatementNode(FunctionCallNode call, Token semicolon)
    {
        super(call.start(), semicolon.end());
        this.call = call;
    }
}
