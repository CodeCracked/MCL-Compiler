package compiler.core.parser.nodes.components;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;

public class IdentifierNode extends AbstractNode
{
    public final String value;
    
    public IdentifierNode(Token token)
    {
        super(token.start(), token.end());
        assert token.contents() instanceof String;
    
        this.value = (String)token.contents();
    }
    
    @Override public String toString() { return '"' + value + '"'; }
}
