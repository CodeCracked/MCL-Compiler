package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.statements.NativeStatementNode;

import java.io.IOException;
import java.io.PrintWriter;

public class NativeStatementGenerator implements ICodeGenRule<NativeStatementNode>
{
    @Override
    public Result<Void> generate(NativeStatementNode component, CodeGenContext context) throws IOException
    {
        if (context.getOpenFile().isPresent())
        {
            PrintWriter file = context.getOpenFile().get();
            file.println("# Native Commands: " + component.start().toString());
            for (String command : component.nativeCommands) file.println(command);
            file.println();
            return Result.of(null);
        }
        else return Result.fail(new IllegalStateException("NativeStatementGenerator requires an open file in the CodeGenContext!"));
    }
}
