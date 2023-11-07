package mcl.parser.grammar.declarations;

import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.declarations.FunctionDeclarationNode;

public class FunctionDeclarationRule implements IGrammarRule<FunctionDeclarationNode>
{
    @Override
    public Result<FunctionDeclarationNode> build(Parser parser)
    {
        Result<FunctionDeclarationNode> result = new Result<>();
        
        // Signature
        FunctionSignatureNode signature = result.register(DefaultRules.FUNCTION_SIGNATURE.build(parser));
        if (result.getFailure() != null) return result;
        
        // Body
        BlockNode body = result.register(MCLRules.FUNCTION_BODY.build(parser));
        if (result.getFailure() != null) return result;
    
        return result.success(new FunctionDeclarationNode(signature, body));
    }
}
