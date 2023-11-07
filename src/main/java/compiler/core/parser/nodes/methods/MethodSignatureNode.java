package compiler.core.parser.nodes.methods;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.source.SourcePosition;

public class MethodSignatureNode extends AbstractNode
{
    public final DataTypeNode returnType;
    public final IdentifierNode identifier;
    public final ParameterListNode parameters;
    
    public MethodSignatureNode(SourcePosition start, SourcePosition end, DataTypeNode returnType, IdentifierNode identifier, ParameterListNode parameters)
    {
        super(start, end, false);
        this.returnType = returnType;
        this.identifier = identifier;
        this.parameters = parameters;
    }
}
