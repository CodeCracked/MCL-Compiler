package compiler.core.parser.nodes.components;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class ParameterListNode extends AbstractNode
{
    public final List<ParameterDeclarationNode> parameters;
    
    public ParameterListNode(Token openingParenthesis, List<ParameterDeclarationNode> parameters, Token closingParenthesis)
    {
        super(openingParenthesis.start(), closingParenthesis.end());
        this.parameters = parameters;
    }
    
    public static ParameterListNode empty(Parser parser)
    {
        return new ParameterListNode(parser.getCurrentToken(), new ArrayList<>(), parser.getCurrentToken());
    }
}
