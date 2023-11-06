package mcl.parser.nodes.natives;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.source.SourcePosition;

import java.util.List;

public class NativeBindSpecifierNode extends AbstractNode
{
    public final IdentifierNode parameter;
    public final IdentifierNode bindType;
    public final List<String> bindArguments;
    
    public NativeBindSpecifierNode(IdentifierNode parameter, IdentifierNode bindType, List<String> bindArguments, SourcePosition end)
    {
        super(parameter.start(), end);
        this.parameter = parameter;
        this.bindType = bindType;
        this.bindArguments = bindArguments;
    }
}
