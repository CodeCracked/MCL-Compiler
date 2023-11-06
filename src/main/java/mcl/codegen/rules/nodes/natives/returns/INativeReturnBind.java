package mcl.codegen.rules.nodes.natives.returns;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.util.Result;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.io.PrintWriter;
import java.util.List;

public interface INativeReturnBind
{
    Result<Void> write(NativeBindSpecifierNode bind, DataTypeNode returnType, List<String> arguments, PrintWriter file, CodeGenContext context);
}
