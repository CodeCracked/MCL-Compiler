package mcl.compiler.exceptions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.symbols.EventSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.parser.nodes.events.ListenerDefinitionNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;

import java.util.List;

public class MCLListenerParametersError extends MCLError
{
    public MCLListenerParametersError(MCLCompiler compiler, ListenerDefinitionNode listener, EventSymbol function)
    {
        super(compiler.getSource().getCodeLocation(listener.location.endPosition() + 1), compiler.getSource().getCodeLocation(listener.endPosition() - 1), "Invalid Parameters", buildDetails(compiler, function.parameters, listener.parameterList.parameters));
    }

    private static String buildDetails(MCLCompiler compiler, List<VariableSymbol> expected, List<VariableSignatureNode> found)
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Expected (");
        for (int i = 0; i < expected.size(); i++)
        {
            builder.append(expected.get(i).type.getMinecraftName());
            if (i != expected.size() - 1) builder.append(", ");
        }

        builder.append("), found (");
        for (int i = 0; i < found.size(); i++)
        {
            builder.append(found.get(i).type.getMinecraftName());
            if (i != found.size() - 1) builder.append(", ");
        }
        builder.append(")");

        return builder.toString();
    }
}
