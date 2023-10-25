package compiler.core.parser.nodes.components;

import compiler.core.parser.AbstractNode;

public class ParameterDeclarationNode extends AbstractNode
{
    public final DataTypeNode dataType;
    public final IdentifierNode identifier;
    
    public ParameterDeclarationNode(DataTypeNode dataType, IdentifierNode identifier)
    {
        super(dataType.start(), identifier.end());
        this.dataType = dataType;
        this.identifier = identifier;
    }
}
