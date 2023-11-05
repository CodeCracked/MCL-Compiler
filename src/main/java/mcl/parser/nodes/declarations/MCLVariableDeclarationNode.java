package mcl.parser.nodes.declarations;

import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.VariableDeclarationNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.symbols.MCLVariableSymbol;

public class MCLVariableDeclarationNode extends VariableDeclarationNode<MCLVariableSymbol>
{
    public MCLVariableDeclarationNode(SourcePosition start, SourcePosition end, DataTypeNode type, IdentifierNode identifier, AbstractValueNode initialValue)
    {
        super(start, end, type, identifier, initialValue);
    }
    
    @Override
    protected Result<MCLVariableSymbol> createSymbol()
    {
        Result<MCLVariableSymbol> result = new Result<>();
        
        // Get Namespace
        NamespaceNode namespace = result.register(findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        
        return result.success(new MCLVariableSymbol(this, namespace.identifier.value, identifier.value, type.value, initialValue));
    }
}
