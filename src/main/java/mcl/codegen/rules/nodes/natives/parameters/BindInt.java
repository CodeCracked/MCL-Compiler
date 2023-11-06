package mcl.codegen.rules.nodes.natives.parameters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;
import mcl.parser.symbols.VariableSymbol;

import java.io.PrintWriter;
import java.util.List;

public class BindInt implements INativeParameterBind
{
    @Override
    public Result<Void> write(NativeBindSpecifierNode bind, ParameterDeclarationNode parameter, List<String> arguments, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        if (parameter.type.value != MCLDataTypes.INTEGER) return result.failure(new CompilerException(parameter.start(), parameter.end(), "Parameter bind type 'bind_int' only supports parameters of type 'int'!"));
        else if (arguments.size() != 2) return result.failure(new CompilerException(bind.start(), bind.end(), "Parameter bind type 'bind_int' requires 2 parameters! Usage: bind_float_components(<player>, <objective>)"));
    
        // Copy Variable to Math IO
        file.printf("execute store result score %1$s %2$s run data get storage mcl:runtime %3$s 1\n", arguments.get(0), arguments.get(1), ((VariableSymbol) parameter.getSymbol()).getNBTKey());
        
        return result.success(null);
    }
}
