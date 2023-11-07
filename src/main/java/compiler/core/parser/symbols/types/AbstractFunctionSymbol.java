package compiler.core.parser.symbols.types;

import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.parser.symbols.AbstractSymbol;
import compiler.core.util.Validations;

import java.util.Optional;

public class AbstractFunctionSymbol extends AbstractSymbol<AbstractFunctionDeclarationNode<?>>
{
    public final FunctionSignatureNode signature;
    
    public AbstractFunctionSymbol(AbstractFunctionDeclarationNode<?> definition, String name)
    {
        super(definition, name);
        this.signature = definition.signature;
    }
    
    public Optional<Integer> calculateIndirection(ArgumentListNode arguments)
    {
        // Ensure Arguments Match Signature
        if (Validations.ensureArgumentTypesMatch(signature.parameters, arguments)) return Optional.empty();
        
        // Calculate Implicit Cast Count
        int indirection = 0;
        for (int i = 0; i < signature.parameters.parameters.size(); i++)
        {
            ParameterDeclarationNode parameter = signature.parameters.parameters.get(i);
            AbstractValueNode argument = arguments.arguments.get(i);
            if (argument.getValueType() != parameter.type.value) indirection++;
        }
        
        return Optional.of(indirection);
    }
}
