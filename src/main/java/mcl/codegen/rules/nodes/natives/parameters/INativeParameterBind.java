package mcl.codegen.rules.nodes.natives.parameters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.util.Result;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.io.PrintWriter;
import java.util.List;

public interface INativeParameterBind
{
    Result<Void> write(NativeBindSpecifierNode bind, ParameterDeclarationNode parameter, List<String> arguments, PrintWriter file, CodeGenContext context);
}
