package mcl.util;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;
import java.io.PrintWriter;

public final class MCLCodeGenMacros
{
    public static void writeNodeHeader(AbstractNode node, PrintWriter file)
    {
        file.println("# " + node.start());
        String[] lines = node.getSourceCode().split("\r?\n");
        
        file.println("# " + lines[0]);
        if (lines.length > 1)
        {
            int indent = lines[1].length() - lines[1].stripLeading().length();
            for (int i = 1; i < lines.length; i++) if (lines[i].length() > indent) file.println("# " + lines[i].substring(indent));
        }
    }
    
    public static void pushStackFrame(PrintWriter file)
    {
        // Push new stack frame
        file.println("# Push Stack Frame");
        file.println("data modify storage mcl:runtime TransientCallStack prepend from storage mcl:runtime TransientCallStack[0]");
        file.println("data modify storage mcl:runtime PersistentCallStack prepend from storage mcl:runtime PersistentCallStack[0]");
        file.println("data remove storage mcl:runtime Return");
        file.println();
    }
    public static void popStackFrame(PrintWriter file)
    {
        file.println("# Pop Stack Frame");
        file.println("data remove storage mcl:runtime TransientCallStack[0]");
        file.println("data remove storage mcl:runtime PersistentCallStack[0]");
        file.println();
    }
    
    public static Result<Void> setParameters(Ref<Integer> startingRegister, ParameterListNode parameters, ArgumentListNode arguments, PrintWriter file, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        for (int i = 0; i < arguments.arguments.size(); i++)
        {
            // Get Parameter and Argument
            ParameterDeclarationNode parameter = parameters.parameters.get(i);
            AbstractValueNode argument = arguments.arguments.get(i);
            
            // Write Header
            String[] argumentLines = argument.getSourceCode().split("\r?\n");
            file.println("# " + parameter.identifier.value + " = " + argumentLines[0].stripTrailing());
            for (int j = 1; j < argumentLines.length; j++) file.println("# " + argumentLines[j]);
            
            // Write Argument Expression
            Integer argumentRegister = result.register(context.getGenerator().getExpressionGenerator().generate(startingRegister, argument, context));
            if (result.getFailure() != null) return result;
            
            // Write implicit cast, if necessary
            if (argument.getValueType() != parameter.type.value)
            {
                // Get Argument Adapter
                DataTypeAdapter argumentAdapter = result.register(context.getGenerator().getTypeAdapter(argument.getValueType()));
                if (result.getFailure() != null) return result;
                
                // Write Argument Cast
                result.register(argumentAdapter.cast(argumentRegister, parameter.type.value, context));
                if (result.getFailure() != null) return result;
            }
            
            // Get Parameter Adapter
            DataTypeAdapter parameterAdapter = result.register(context.getGenerator().getTypeAdapter(parameter.type.value));
            if (result.getFailure() != null) return result;
    
            // Write Parameter Assignment
            result.register(parameterAdapter.copyRegisterToVariable(argumentRegister, parameter.getSymbol(), context));
            if (result.getFailure() != null) return result;
            
            // Empty Line
            file.println();
        }
        
        return result.success(null);
    }
    public static void applyReferenceParameters(ParameterListNode parameters)
    {
    
    }
}
