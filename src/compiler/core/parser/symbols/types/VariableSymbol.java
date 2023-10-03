package compiler.core.parser.symbols.types;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;

import java.util.Optional;

public class VariableSymbol extends AbstractTypedSymbol
{
    private final AbstractValueNode defaultValue;
    
    private VariableSymbol(AbstractNode definition, String name, DataType type, AbstractValueNode defaultValue)
    {
        super(definition, name, type);
        this.defaultValue = defaultValue;
    }
    
    public static VariableSymbol uninitialized(AbstractNode definition, String name, DataType type)
    {
        return new VariableSymbol(definition, name, type, null);
    }
    public static Result<VariableSymbol> initialized(AbstractNode definition, String name, DataType type, AbstractValueNode defaultValue)
    {
        if (type.isAssignableFrom(defaultValue.getValueType())) return Result.of(new VariableSymbol(definition, name, type, defaultValue));
        else return Result.fail(new IllegalArgumentException("Cannot assign variable of type '" + type.name() + "' to a value of type '" + defaultValue.getValueType() + "'!"));
    }
    
    public Optional<AbstractValueNode> getDefaultValue() { return Optional.ofNullable(defaultValue); }
    
    @Override
    protected boolean canBeReferencedBy(AbstractNode caller)
    {
        return definition().end().isBefore(caller.start());
    }
}
