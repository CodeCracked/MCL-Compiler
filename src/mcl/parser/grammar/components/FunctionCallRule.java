package mcl.parser.grammar.components;

import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.parser.nodes.components.QualifiedIdentifierNode;

public class FunctionCallRule implements IGrammarRule<FunctionCallNode>
{
    @Override
    public Result<FunctionCallNode> build(Parser parser)
    {
        Result<FunctionCallNode> result = new Result<>();
        
        // Function
        QualifiedIdentifierNode function = result.register(MCLRules.QUALIFIED_IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Arguments
        ArgumentListNode arguments = result.register(MCLRules.ARGUMENT_LIST.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new FunctionCallNode(function, arguments));
    }
}
