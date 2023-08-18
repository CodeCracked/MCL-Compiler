package compiler.core.parser.nodes;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    
    @Override
    public void forEachChild(Consumer<AbstractNode> consumer, boolean recursive)
    {
        parameters.forEach(consumer);
        if (recursive) for (ParameterDeclarationNode parameter : parameters) parameter.forEachChild(consumer, true);
    }
}
