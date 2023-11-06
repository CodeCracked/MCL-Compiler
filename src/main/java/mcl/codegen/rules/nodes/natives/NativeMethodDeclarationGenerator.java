package mcl.codegen.rules.nodes.natives;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.codegen.rules.nodes.natives.parameters.BindFloat;
import mcl.codegen.rules.nodes.natives.parameters.BindFloat32;
import mcl.codegen.rules.nodes.natives.parameters.BindInt;
import mcl.codegen.rules.nodes.natives.parameters.INativeParameterBind;
import mcl.codegen.rules.nodes.natives.returns.INativeReturnBind;
import mcl.codegen.rules.nodes.natives.returns.ReturnFloat;
import mcl.codegen.rules.nodes.natives.returns.ReturnFloat32;
import mcl.codegen.rules.nodes.natives.returns.ReturnInt;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;
import mcl.parser.nodes.natives.NativeMethodDeclarationNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class NativeMethodDeclarationGenerator implements ICodeGenRule<NativeMethodDeclarationNode>
{
    private final Map<String, INativeParameterBind> PARAMETER_BIND_TYPES = Map.of
    (
            "bind_int", new BindInt(),
            "bind_float", new BindFloat(),
            "bind_float_32", new BindFloat32()
    );
    private final Map<String, INativeReturnBind> RETURN_BIND_TYPES = Map.of
    (
            "return_int", new ReturnInt(),
            "return_float", new ReturnFloat(),
            "return_float_32", new ReturnFloat32()
    );
    
    @Override
    public Result<Void> generate(NativeMethodDeclarationNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Open Bind File
        context.openSubdirectory("functions", "binds");
        PrintWriter file = context.openFile(component.identifier.value + ".mcfunction", write ->
        {
            write.println("# " + component.getMCLDescription());
            write.println();
        });
        
        // Generate Parameter Binds
        for (NativeBindSpecifierNode parameterBind : component.binds.parameterBinds)
        {
            result.register(generateParameterBind(component, parameterBind, file, context));
            if (result.getFailure() != null) return result;
        }
        
        // Generate Native Function Call
        file.println("# Native Call");
        file.println("function " + component.nativeFunction.contents());
        file.println();
        
        // Generate Return Bind
        if (component.binds.returnBind != null)
        {
            result.register(generateReturnBind(component, component.binds.returnBind, file, context));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(null);
    }
    
    private Result<Void> generateParameterBind(NativeMethodDeclarationNode nativeMethod, NativeBindSpecifierNode bind, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Print Header
        file.println("# " + bind.getMCLDescription());
        
        // Find Parameter
        ParameterDeclarationNode parameter = null;
        for (ParameterDeclarationNode test : nativeMethod.parameters.parameters)
        {
            if (test.identifier.value.equals(bind.parameter.value))
            {
                parameter = test;
                break;
            }
        }
        if (parameter == null) return result.failure(new CompilerException(bind.parameter.start(), bind.parameter.end(), "No matching method parameter found!"));
        
        // Generate Bind
        if (PARAMETER_BIND_TYPES.containsKey(bind.bindType.value))
        {
            result.register(PARAMETER_BIND_TYPES.get(bind.bindType.value).write(bind, parameter, bind.bindArguments, file, context));
            if (result.getFailure() != null) return result;
        }
        else return result.failure(new CompilerException(bind.bindType.start(), bind.bindType.end(), "Unknown parameter bind type '" + bind.bindType.value + "'!"));
    
        file.println();
        return result.success(null);
    }
    private Result<Void> generateReturnBind(NativeMethodDeclarationNode nativeMethod, NativeBindSpecifierNode bind, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        // Print Header
        file.println("# " + bind.getMCLDescription());
    
        // Generate Bind
        if (RETURN_BIND_TYPES.containsKey(bind.bindType.value))
        {
            result.register(RETURN_BIND_TYPES.get(bind.bindType.value).write(bind, nativeMethod.returnType, bind.bindArguments, file, context));
            if (result.getFailure() != null) return result;
        }
        else return result.failure(new CompilerException(bind.bindType.start(), bind.bindType.end(), "Unknown return bind type '" + bind.bindType.value + "'!"));
    
        file.println();
        return result.success(null);
    }
}
