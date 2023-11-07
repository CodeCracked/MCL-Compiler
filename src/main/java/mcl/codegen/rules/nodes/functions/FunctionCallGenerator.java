package mcl.codegen.rules.nodes.functions;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.expression.IExpressionGenRule;
import compiler.core.util.Ref;
import compiler.core.util.Result;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.util.MCLCodeGenMacros;

import java.io.IOException;
import java.io.PrintWriter;

public class FunctionCallGenerator implements IExpressionGenRule<FunctionCallNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, FunctionCallNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
        int register = startingRegister.get();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Print Header
        MCLCodeGenMacros.writeNodeHeader(component, file);
        
        // Push Stack Frame
        MCLCodeGenMacros.pushStackFrame(file);
        
        // TODO: Generate parameters and function call
        
        // Pop Stack Frame
        MCLCodeGenMacros.popStackFrame(file);
        
        return result.success(register);
    }
}
