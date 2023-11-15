package mcl.codegen.rules.nodes.statements;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.DataTypeAdapter;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.nodes.statements.ReturnStatementNode;
import mcl.util.MCLCodeGenMacros;

import java.io.IOException;
import java.io.PrintWriter;

public class ReturnStatementGenerator implements ICodeGenRule<ReturnStatementNode>
{
    @Override
    public Result<Void> generate(ReturnStatementNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
    
        // Print Header
        MCLCodeGenMacros.writeNodeHeader(component, file);
        
        // Set Return Value
        if (component.expression != null)
        {
            // Get Adapter
            DataTypeAdapter adapter = result.register(context.getGenerator().getTypeAdapter(component.expression.getValueType()));
            if (result.getFailure() != null) return result;
            
            // Generate Expression
            result.register(context.generate(component.expression));
            if (result.getFailure() != null) return result;
    
            // Set Return
            result.register(adapter.copyRegisterToReturn(0, context));
            if (result.getFailure() != null) return result;
        }
        else file.println("data modify storage mcl:runtime Return set value 0");
        
        return result.success(null);
    }
}
