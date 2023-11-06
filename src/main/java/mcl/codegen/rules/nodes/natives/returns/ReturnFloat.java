package mcl.codegen.rules.nodes.natives.returns;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.io.PrintWriter;
import java.util.List;

public class ReturnFloat implements INativeReturnBind
{
    @Override
    public Result<Void> write(NativeBindSpecifierNode bind, DataTypeNode returnType, List<String> arguments, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        if (returnType.value != MCLDataTypes.FLOAT) return result.failure(new CompilerException(returnType.start(), returnType.end(), "Return bind type 'return_float' only supports parameters of type 'float'!"));
        else if (arguments.size() != 4) return result.failure(new CompilerException(bind.start(), bind.end(), "Return bind type 'return_float' requires 4 parameters! Usage: return_float(<sign>, <exponent>, <mantissa>, <destination>)"));
        
        // Copy Float Components to Registers
        file.printf("scoreboard players operation P0 mcl.math.io = %1$s %2$s\n", arguments.get(0), arguments.get(3));
        file.printf("scoreboard players operation P0 mcl.math.io = %1$s %2$s\n", arguments.get(1), arguments.get(3));
        file.printf("scoreboard players operation P0 mcl.math.io = %1$s %2$s\n", arguments.get(2), arguments.get(3));
        
        // Recompose
        file.println("function mcl:math/float/32/recompose/main");
        
        // Copy Math IO to Return Container
        file.printf("execute store result storage mcl:runtime Return int 1 run scoreboard players get R0 mcl.math.io\n");
    
        return result.success(null);
    }
}
