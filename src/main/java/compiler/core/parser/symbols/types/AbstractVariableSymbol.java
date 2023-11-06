package compiler.core.parser.symbols.types;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.AbstractVariableDeclarationNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.types.DataType;

import java.util.Optional;

public abstract class AbstractVariableSymbol extends AbstractTypedSymbol<AbstractVariableDeclarationNode<?>>
{
    private final AbstractValueNode defaultValue;
    
    public AbstractVariableSymbol(AbstractVariableDeclarationNode<?> definition, String name, DataType type, AbstractValueNode defaultValue)
    {
        super(definition, name, type);
        this.defaultValue = defaultValue;
    }
    
    public Optional<AbstractValueNode> getDefaultValue() { return Optional.ofNullable(defaultValue); }
    
    @Override
    protected boolean canBeReferencedBy(AbstractNode caller)
    {
        return definition().end().isBefore(caller.start());
    }
}
