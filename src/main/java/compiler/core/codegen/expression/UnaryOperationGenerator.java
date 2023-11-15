package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.parser.nodes.expression.UnaryOperationNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;

public class UnaryOperationGenerator implements IExpressionGenRule<UnaryOperationNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, UnaryOperationNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
        
        // Argument
        Integer argumentRegister = result.register(context.getGenerator().getExpressionGenerator().generate(startingRegister, component.expression, context));
        if (result.getFailure() != null) return result;
    
        // Get Type Adapter
        DataTypeAdapter adapter = result.register(context.getGenerator().getTypeAdapter(component.getValueType()));
        if (result.getFailure() != null) return result;
    
        // Operation
        Enum<?> operation = component.operation.type();
        if (operation == MathTokenType.SUBTRACT) result.register(adapter.negate(argumentRegister, context));
        if (result.getFailure() != null) return result;
        
        return result.success(argumentRegister);
    }
}
