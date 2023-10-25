package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.parser.nodes.components.VariableDeclarationNode;
import compiler.core.parser.symbols.types.VariableSymbol;
import compiler.core.util.Result;

import java.io.IOException;
import java.io.PrintWriter;

public class VariableDeclarationGenerator implements ICodeGenRule<VariableDeclarationNode>
{
    @Override
    public Result<Void> generate(VariableDeclarationNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
    
        // Get Current File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Get Adapter
        VariableSymbol variable = component.getSymbol();
        DataTypeAdapter adapter = result.register(context.getGenerator().getTypeAdapter(variable.getType()));
        if (result.getFailure() != null) return result;
        
        // Write Header
        file.println("# " + component.getMCLDescription());
        
        // Write Variable Initializer
        if (component.initialValue == null) result.register(adapter.resetVariable(variable, context));
        else
        {
            // Generate Initializer Expression
            result.register(context.generate(component.initialValue));
            if (result.getFailure() != null) return result;
            
            // Generate Variable Assignment
            result.register(adapter.copyFromRegister(0, variable, context));
        }
        if (result.getFailure() != null) return result;
        
        // Write Blank Line
        file.println();
        return result.success(null);
    }
}
