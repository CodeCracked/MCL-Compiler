package compiler.core.parser.nodes.components;

import compiler.core.util.Result;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.symbols.VariableSymbol;

public class ParameterDeclarationNode extends AbstractVariableDeclarationNode<VariableSymbol>
{
    public final DataTypeNode dataType;
    public final IdentifierNode identifier;
    
    public ParameterDeclarationNode(DataTypeNode dataType, IdentifierNode identifier)
    {
        super(dataType.start(), identifier.end(), dataType, identifier, null, false);
        this.dataType = dataType;
        this.identifier = identifier;
    }
    
    @Override
    protected Result<VariableSymbol> instantiateSymbol()
    {
        Result<VariableSymbol> result = new Result<>();
    
        // Get Namespace
        NamespaceNode namespace = result.register(findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
        
        return result.success(new VariableSymbol(this, namespace.identifier.value, identifier.value, type.value, initialValue));
    }
}
