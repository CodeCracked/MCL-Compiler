package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.parser.nodes.expression.CastOperationNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;

public class CastOperationGenerator implements IExpressionGenRule<CastOperationNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, CastOperationNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
        
        // Generate Argument
        Integer argumentRegister = result.register(ExpressionGenerator.generate(startingRegister, component.expression, context));
        if (result.getFailure() != null) return result;
        
        // Get Type Adapter
        DataTypeAdapter adapter = result.register(context.getCodeGenerator().getTypeAdapter(component.expression.getValueType()));
        if (result.getFailure() != null) return result;
        
        // Write Cast
        result.register(adapter.cast(argumentRegister, component.castType.value, context));
        if (result.getFailure() != null) return result;
        
        return result.success(argumentRegister);
    }
}
