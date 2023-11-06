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

public class BindFloat implements INativeParameterBind
{
    @Override
    public Result<Void> write(NativeBindSpecifierNode bind, ParameterDeclarationNode parameter, List<String> arguments, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
        
        if (parameter.type.value != MCLDataTypes.FLOAT) return result.failure(new CompilerException(parameter.start(), parameter.end(), "Parameter bind type 'bind_float_components' only supports parameters of type 'float'!"));
        else if (arguments.size() != 4) return result.failure(new CompilerException(bind.start(), bind.end(), "Parameter bind type 'bind_float' requires 4 parameters! Usage: bind_float(<sign>, <exponent>, <mantissa>, <destination>)"));
    
        // Copy Variable to Math IO
        file.printf("execute store result score P0 mcl.math.io run data get storage mcl:runtime " + ((VariableSymbol) parameter.getSymbol()).getNBTKey() + " 1\n");
    
        // Decompose
        file.println("function mcl:math/float/32/decompose/main");
    
        // Copy Float Components to Registers
        file.printf("scoreboard players operation %1$s %2$s = R0 mcl.math.io\n", arguments.get(0), arguments.get(3));
        file.printf("scoreboard players operation %1$s %2$s = R1 mcl.math.io\n", arguments.get(1), arguments.get(3));
        file.printf("scoreboard players operation %1$s %2$s = R2 mcl.math.io\n", arguments.get(2), arguments.get(3));
        
        return result.success(null);
    }
}
