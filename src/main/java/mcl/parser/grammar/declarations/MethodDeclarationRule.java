package mcl.parser.grammar.declarations;

import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.methods.MethodSignatureNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.declarations.MethodDeclarationNode;

public class MethodDeclarationRule implements IGrammarRule<MethodDeclarationNode>
{
    @Override
    public Result<MethodDeclarationNode> build(Parser parser)
    {
        Result<MethodDeclarationNode> result = new Result<>();
        
        // Signature
        MethodSignatureNode signature = result.register(DefaultRules.METHOD_SIGNATURE.build(parser));
        if (result.getFailure() != null) return result;
        
        // Body
        BlockNode body = result.register(MCLRules.FUNCTION_BODY.build(parser));
        if (result.getFailure() != null) return result;
    
        return result.success(new MethodDeclarationNode(signature, body));
    }
}
