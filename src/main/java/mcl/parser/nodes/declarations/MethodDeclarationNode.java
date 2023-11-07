package mcl.parser.nodes.declarations;

import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.methods.AbstractMethodDeclarationNode;
import compiler.core.parser.nodes.methods.MethodSignatureNode;

public class MethodDeclarationNode extends AbstractMethodDeclarationNode
{
    public BlockNode body;
    
    public MethodDeclarationNode(MethodSignatureNode signature, BlockNode body)
    {
        super(signature.start(), body.end(), signature);
        this.body = body;
    }
}
