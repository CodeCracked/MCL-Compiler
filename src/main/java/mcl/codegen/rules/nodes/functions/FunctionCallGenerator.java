package mcl.codegen.rules.nodes.functions;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.expression.IExpressionGenRule;
import compiler.core.parser.AbstractNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.parser.nodes.declarations.FunctionDeclarationNode;
import mcl.parser.nodes.natives.NativeFunctionDeclarationNode;
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
        MCLCodeGenMacros.setParameters(startingRegister, component.getSymbol().signature.parameters, component.arguments, file, context);
        
        // Call Function's main.mcfunction File
        file.println("# Call Function");
        AbstractNode declaration = component.getSymbol().definition();
        if (declaration instanceof FunctionDeclarationNode mclFunction) file.printf("function %1$s:functions/%2$s/main\n", component.getSymbol().namespace, mclFunction.functionName());
        else if (declaration instanceof NativeFunctionDeclarationNode nativeFunction) file.printf("function %1$s:binds/%2$s\n", component.getSymbol().namespace, nativeFunction.signature.identifier.value);
        else return result.failure(new CompilerException(declaration.start(), declaration.end(), "Unknown function type '" + declaration.getClass().getSimpleName() + "'!"));
        file.println();
        
        // TODO: Process the return value
        
        // Pop Stack Frame
        MCLCodeGenMacros.applyReferenceParameters(component.getSymbol().signature.parameters);
        MCLCodeGenMacros.popStackFrame(file);
        
        return result.success(register);
    }
}
