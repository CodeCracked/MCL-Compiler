package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.parser.nodes.expression.LiteralNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;

public class LiteralGenerator implements IExpressionGenRule<LiteralNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, LiteralNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
        
        // Get and Increment Register
        Integer register = startingRegister.get();
        startingRegister.set(register + 1);
    
        // Get Type Adapter
        DataTypeAdapter adapter = result.register(context.getCodeGenerator().getTypeAdapter(component.getValueType()));
        if (result.getFailure() != null) return result;
        
        // Register Load
        result.register(adapter.copyToRegister(register, component.value, context));
        if (result.getFailure() != null) return result;
        
        return result.success(register);
    }
}
