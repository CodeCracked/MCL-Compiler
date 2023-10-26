package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.RootNode;
import compiler.core.util.Result;
import mcl.codegen.MCLStandardLibrary;

import java.io.IOException;
import java.io.PrintWriter;

public class RootGenerator implements ICodeGenRule<RootNode>
{
    @Override
    public Result<Void> generate(RootNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Copy standard library
        result.register(MCLStandardLibrary.installNatives(context.getCurrentDirectory()));
        if (result.getFailure() != null) return result;
        
        // 'pack.mcmeta' file
        context.writeFile("pack.mcmeta", writer -> writeMeta(writer, 15), false);
        
        // 'data' directory
        context.openSubdirectory("data");
        
        // Generate sources
        for (AbstractNode source : component.sources)
        {
            result.register(context.getGenerator().generate(source, context));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(null);
    }
    
    private void writeMeta(PrintWriter writer, int formatVersion)
    {
        writer.println("{");
        writer.println("    \"pack\": {");
        writer.println("        \"pack_format\": " + formatVersion + ",");
        writer.println("        \"description\": \"A data pack compiled from MCL.\"");
        writer.println("    }");
        writer.println("}");
    }
}
