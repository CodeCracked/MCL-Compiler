package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeCodeAdapter;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.parser.nodes.expression.BinaryOperationNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;

public class BinaryOperationGenerator implements IExpressionGenRule<BinaryOperationNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, BinaryOperationNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
    
        // Arguments
        Integer leftRegister = result.register(ExpressionGenerator.generate(startingRegister, component.left, context));
        Integer rightRegister = result.register(ExpressionGenerator.generate(startingRegister, component.right, context));
    
        // Get Type Adapter
        DataTypeCodeAdapter adapter = result.register(context.getCodeGenerator().getTypeAdapter(component.getValueType()));
        if (result.getFailure() != null) return result;
    
        // Operation
        Enum<?> operation = component.operation.type();
        if (operation == MathTokenType.ADD)              result.register(adapter.add(leftRegister, rightRegister, context));
        else if (operation == MathTokenType.SUBTRACT)    result.register(adapter.subtract(leftRegister, rightRegister, context));
        else if (operation == MathTokenType.MULTIPLY)    result.register(adapter.multiply(leftRegister, rightRegister, context));
        else if (operation == MathTokenType.DIVIDE)      result.register(adapter.divide(leftRegister, rightRegister, context));
        else if (operation == MathTokenType.MODULUS)     result.register(adapter.modulo(leftRegister, rightRegister, context));
    
        if (result.getFailure() != null) return result;
        return result.success(leftRegister);
    }
}
