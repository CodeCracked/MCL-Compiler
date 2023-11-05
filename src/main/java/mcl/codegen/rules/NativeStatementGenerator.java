package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerWarning;
import mcl.parser.nodes.statements.NativeStatementNode;
import mcl.parser.symbols.MCLVariableSymbol;
import mcl.util.Lookups;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeStatementGenerator implements ICodeGenRule<NativeStatementNode>
{
    private static final Pattern NATIVE_MACRO = Pattern.compile("%\\{([^}]+)}");
    private static final List<BiFunction<NativeStatementNode, String, String>> MACRO_RESOLVERS = List.of
    (
        NativeStatementGenerator::variableResolver
    );
    
    @Override
    public Result<Void> generate(NativeStatementNode component, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
    
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
    
        // Write Native Commands
        file.println("# Native Commands: " + component.start().toString());
        for (String command : component.nativeCommands)
        {
            String expandedMacros = result.register(applyNativeMacros(component, command));
            if (result.getFailure() != null) return result;
            else file.println(expandedMacros);
        }
        file.println();
    
        return result.success(null);
    }
    private Result<String> applyNativeMacros(NativeStatementNode node, String command)
    {
        Result<String> result = new Result<>();
        Matcher matcher = NATIVE_MACRO.matcher(command);
        
        // Macro resolution loop
        StringBuilder builder = new StringBuilder();
        while (matcher.find())
        {
            // Attempt to resolve macro
            String macro = matcher.group(1);
            String resolved = null;
            for (BiFunction<NativeStatementNode, String, String> resolver : MACRO_RESOLVERS)
            {
                resolved = resolver.apply(node, macro);
                if (resolved != null) break;
            }
            
            // Handle resolution
            if (resolved == null)
            {
                SourcePosition macroStart = node.start();
                SourcePosition macroEnd = node.end();
                result.addWarning(new CompilerWarning(macroStart, macroEnd, "Failed to resolve native macro '" + macro + "'!"));
                matcher.appendReplacement(builder, matcher.group());
            }
            else matcher.appendReplacement(builder, resolved);
        }
        matcher.appendTail(builder);
        
        return result.success(builder.toString());
    }
    
    private static String variableResolver(NativeStatementNode node, String macro)
    {
        // Tokenize
        String[] tokens = macro.split("\\.");
        if (tokens.length != 2) return null;
        
        // Get Variable
        String[] variableTokens = tokens[0].split(":");
        if (variableTokens.length < 1 || variableTokens.length > 2) return null;
        Result<SymbolTable.SymbolEntry<MCLVariableSymbol>> lookup = variableTokens.length == 2 ? Lookups.variable(node, variableTokens[0], variableTokens[1]) : Lookups.variable(node, null, variableTokens[0]);
        if (lookup.getFailure() != null) return null;
        MCLVariableSymbol variable = lookup.get().symbol();
        
        // Get Property
        if (tokens[1].equals("nbt_key")) return variable.getNBTKey();
        else if (tokens[1].equals("text_component")) return "{\"nbt\":\"" + variable.getNBTKey() + "\",\"storage\":\"mcl:runtime\"}";
        else return null;
    }
}
