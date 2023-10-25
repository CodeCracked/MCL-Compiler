package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;
import mcl.MCL;
import mcl.lexer.MCLDataTypes;

import java.io.PrintWriter;

public class FloatDataTypeAdapter extends NumericDataTypeAdapter
{
    public FloatDataTypeAdapter(int decimalPlaces)
    {
        super(MCLDataTypes.FLOAT, decimalPlaces);
    }
    
    @Override
    public Result<Void> cast(int register, DataType castTo, CodeGenContext context)
    {
        Result<Void> result = new Result<>();
    
        if (castTo == MCLDataTypes.INTEGER)
        {
            // Get Open File
            PrintWriter file = result.register(context.getOpenFile());
            if (result.getFailure() != null) return result;
        
            // Write Conversion Factor
            file.printf("scoreboard players set rTemp mcl.registers 1");
            for (int i = 0; i < MCL.FLOAT_DECIMAL_PLACES; i++) file.print('0');
            file.println();
        
            // Write Cast
            file.printf("scoreboard players operation r%1$d mcl.registers /= rTemp mcl.registers", register);
            file.println();
        
            return result.success(null);
        }
        else return result.failure(new UnsupportedOperationException("Cannot cast " + getType().name() + " to " + castTo.name() + "!"));
    }
}
