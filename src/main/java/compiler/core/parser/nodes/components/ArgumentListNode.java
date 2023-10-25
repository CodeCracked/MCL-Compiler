package compiler.core.parser.nodes.components;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.expression.AbstractValueNode;

import java.util.ArrayList;
import java.util.List;

public class ArgumentListNode extends AbstractNode
{
    public final List<AbstractValueNode> arguments;
    
    public ArgumentListNode(Token openingParenthesis, List<AbstractValueNode> arguments, Token closingParenthesis)
    {
        super(openingParenthesis.start(), closingParenthesis.end());
        this.arguments = arguments;
    }
    
    public static ArgumentListNode empty(Parser parser)
    {
        return new ArgumentListNode(parser.getCurrentToken(), new ArrayList<>(), parser.getCurrentToken());
    }
}
