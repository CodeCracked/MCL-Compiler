package mcl.parser.nodes.declarations;

import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.util.Result;

public class FunctionDeclarationNode extends AbstractFunctionDeclarationNode
{
    public final BlockNode body;
    
    private String functionName;
    
    public FunctionDeclarationNode(FunctionSignatureNode signature, BlockNode body)
    {
        super(signature.start(), body.end(), signature);
        this.body = body;
    }
    
    public String functionName() { return functionName; }
    
    @Override
    protected Result<Void> retrieveSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Build Function Name
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(Character.toLowerCase(signature.returnType.value.name().charAt(0)));
        nameBuilder.append('_');
        nameBuilder.append(signature.identifier.value);
        
        // Append Parameter Types to Function Name
        if (signature.parameters.parameters.size() > 0)
        {
            nameBuilder.append('_');
            for (ParameterDeclarationNode parameter : signature.parameters.parameters) nameBuilder.append(Character.toLowerCase(parameter.type.value.name().charAt(0)));
        }
        this.functionName = nameBuilder.toString();
        
        return result.success(null);
    }
}
