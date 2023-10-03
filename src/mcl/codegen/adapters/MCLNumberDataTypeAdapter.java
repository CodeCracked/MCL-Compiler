package mcl.codegen.adapters;

import compiler.core.codegen.CodeGenContext;
import compiler.core.util.Result;

public class MCLNumberDataTypeAdapter extends AbstractMCLDataTypeAdapter
{
    private final float toScale;
    private final float fromScale;
    
    private MCLNumberDataTypeAdapter(float toScale, float fromScale)
    {
        this.toScale = toScale;
        this.fromScale = fromScale;
    }
    public static MCLNumberDataTypeAdapter integer() { return new MCLNumberDataTypeAdapter(1, 1); }
    public static MCLNumberDataTypeAdapter decimal(int precision) { return new MCLNumberDataTypeAdapter((float)Math.pow(10, precision), (float)Math.pow(10, -precision)); }
    
    @Override
    public Result<Void> copyToRegister(int register, Object literal, CodeGenContext context)
    {
        return writeCommand(context, "scoreboard players set r{0} mcl.registers {1}", register, literal);
    }
}
