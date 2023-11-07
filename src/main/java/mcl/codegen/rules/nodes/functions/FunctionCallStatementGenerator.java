package mcl.codegen.rules.nodes.functions;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.statements.FunctionCallStatementNode;

import java.io.IOException;

public class FunctionCallStatementGenerator implements ICodeGenRule<FunctionCallStatementNode>
{
    @Override
    public Result<Void> generate(FunctionCallStatementNode component, CodeGenContext context) throws IOException
    {
        return context.generate(component.call);
    }
}
