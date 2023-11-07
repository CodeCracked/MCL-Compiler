package mcl.util;

import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.components.ParameterListNode;

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
}
