package mcl.compiler.exceptions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.expressions.FunctionCallNode;

import java.util.List;

public class MCLFunctionCallError extends MCLError
{
    public MCLFunctionCallError(MCLCompiler compiler, FunctionCallNode call, FunctionSymbol function)
    {
        super(compiler.getSource().getCodeLocation(call.location.endPosition() + 1), compiler.getSource().getCodeLocation(call.endPosition() - 1), "Invalid Arguments", buildDetails(compiler, function.parameters, call.arguments));
    }

    private static String buildDetails(MCLCompiler compiler, List<VariableSymbol> expected, List<AbstractNode> found)
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
            builder.append(found.get(i).getRuntimeType(compiler).getMinecraftName());
            if (i != found.size() - 1) builder.append(", ");
        }
        builder.append(")");

        return builder.toString();
    }
}
