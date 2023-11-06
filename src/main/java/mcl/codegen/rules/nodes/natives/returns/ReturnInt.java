package mcl.codegen.rules.nodes.natives.returns;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.io.PrintWriter;
import java.util.List;

public class ReturnInt implements INativeReturnBind
{
    @Override
    public Result<Void> write(NativeBindSpecifierNode bind, DataTypeNode returnType, List<String> arguments, PrintWriter file, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        if (returnType.value != MCLDataTypes.INTEGER) return result.failure(new CompilerException(returnType.start(), returnType.end(), "Return bind type 'return_int' only supports parameters of type 'int'!"));
        else if (arguments.size() != 2) return result.failure(new CompilerException(bind.start(), bind.end(), "Return bind type 'return_int' requires 2 parameters! Usage: return_int(<player>, <objective>)"));
        
        // Copy Math IO to Return Container
        file.printf("execute store result storage mcl:runtime Return int 1 run scoreboard players get %1$s %2$s\n", arguments.get(0), arguments.get(1));
    
        return result.success(null);
    }
}
