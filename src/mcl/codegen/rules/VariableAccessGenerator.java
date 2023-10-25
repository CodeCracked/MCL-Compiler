package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.codegen.expression.IExpressionGenRule;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Ref;
import compiler.core.util.Result;
import mcl.parser.nodes.components.VariableAccessNode;

import java.io.IOException;

public class VariableAccessGenerator implements IExpressionGenRule<VariableAccessNode>
{
    @Override
    public Result<Integer> generate(Ref<Integer> startingRegister, VariableAccessNode component, CodeGenContext context) throws IOException
    {
        Result<Integer> result = new Result<>();
    
        // Get Register
        int register = startingRegister.get();
        startingRegister.set(register + 1);
        
        // Get Adapter
        VariableSymbol variable = component.getSymbol();
        DataTypeAdapter adapter = result.register(context.getGenerator().getTypeAdapter(variable.getType()));
        if (result.getFailure() != null) return result;
    
        // Write Variable Access
        result.register(adapter.copyToRegister(register, component.getSymbol(), context));
        
        if (result.getFailure() != null) return result;
        return result.success(register);
    }
}
