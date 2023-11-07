package mcl.codegen.rules.symbols;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.util.Result;
import mcl.parser.symbols.EventSymbol;

import java.io.IOException;

public class EventGenerator implements ICodeGenRule<EventSymbol>
{
    @Override
    public Result<Void> generate(EventSymbol component, CodeGenContext context) throws IOException
    {
        context.openSubdirectory("data", component.namespace, "tags", "functions");
        
        context.writeFile(component.name() + ".json", file ->
        {
            file.println("{");
            file.println("    \"__MCL__\": \"" + component.definition().getSourceCode().split("\r?\n")[0] + "\",");
            file.println("    \"values\":");
            file.println("    [");
            
            for (int i = 0; i < component.listenerFunctions.size(); i++)
            {
                file.print("        \"" + component.listenerFunctions.get(i) + "\"");
                if (i == component.listenerFunctions.size() - 1) file.println();
                else file.println(",");
            }
            
            file.println("    ]");
            file.println("}");
        });
        
        return Result.of(null);
    }
}
