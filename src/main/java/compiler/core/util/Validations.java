package compiler.core.util;

import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;

public final class Validations
{
    public static boolean ensureParameterListsMatch(ParameterListNode expected, ParameterListNode found)
    {
        if (expected.parameters.size() != found.parameters.size()) return false;
        for (int i = 0; i < expected.parameters.size(); i++)
        {
            ParameterDeclarationNode expectedParameter = expected.parameters.get(i);
            ParameterDeclarationNode foundParameter = found.parameters.get(i);
            if (expectedParameter.type.value != foundParameter.type.value) return false;
        }
        return true;
    }
    public static boolean ensureArgumentTypesMatch(ParameterListNode parameters, ArgumentListNode arguments)
    {
        // Check argument count
        if (parameters.parameters.size() != arguments.arguments.size()) return false;
        
        // Check argument types
        for (int i = 0; i < arguments.arguments.size(); i++)
        {
            // Get parameter and argument
            ParameterDeclarationNode parameter = parameters.parameters.get(i);
            AbstractValueNode argument = arguments.arguments.get(i);
            
            // Check types
            if (!argument.getValueType().canImplicitCast(parameter.type.value)) return false;
        }
        return true;
    }
}
