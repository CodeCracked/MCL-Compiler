package mcl.compiler.exceptions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.parser.nodes.expressions.FunctionCallNode;

public class MCLFunctionCallError extends MCLError
{
    public MCLFunctionCallError(MCLCompiler compiler, FunctionCallNode call, FunctionSymbol function)
    {
        super(compiler.getSource().getCodeLocation(call.identifier.endPosition() + 1), compiler.getSource().getCodeLocation(call.endPosition() - 1), "Invalid Function Arguments", buildDetails(compiler, call, function));
    }

    private static String buildDetails(MCLCompiler compiler, FunctionCallNode call, FunctionSymbol function)
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Expected (");
        for (int i = 0; i < function.parameters.size(); i++)
        {
            builder.append(function.parameters.get(i).type.getMinecraftName());
            if (i != function.parameters.size() - 1) builder.append(", ");
        }

        builder.append("), found (");
        for (int i = 0; i < call.arguments.size(); i++)
        {
            builder.append(call.arguments.get(i).getRuntimeType(compiler).getMinecraftName());
            if (i != call.arguments.size() - 1) builder.append(", ");
        }
        builder.append(")");

        return builder.toString();
    }
}
