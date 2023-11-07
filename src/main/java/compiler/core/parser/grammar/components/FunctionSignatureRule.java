package compiler.core.parser.grammar.components;

import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.util.Result;

public class FunctionSignatureRule implements IGrammarRule<FunctionSignatureNode>
{
    @Override
    public Result<FunctionSignatureNode> build(Parser parser)
    {
        Result<FunctionSignatureNode> result = new Result<>();
        
        // Return Type
        DataTypeNode returnType = result.register(DefaultRules.DATA_TYPE.build(parser));
        if (result.getFailure() != null) return result;
    
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
    
        // Parameter List
        ParameterListNode parameters = result.register(DefaultRules.PARAMETER_LIST.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new FunctionSignatureNode(returnType.start(), parameters.end(), returnType, identifier, parameters));
    }
}