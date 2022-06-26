package mcl.compiler.analyzer.symbols;

import mcl.compiler.MCLKeywords;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FunctionSymbol extends Symbol
{
    public final List<VariableSymbol> parameters;
    public final RuntimeType returnType;
    public Path mainFunctionFile;

    public FunctionSymbol(Token identifier, List<VariableSymbol> parameters, RuntimeType returnType)
    {
        super(identifier, SymbolType.FUNCTION);
        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType;
    }

    @Override
    public MCLError transpileHeader(MCLTranspiler transpiler, Path target)
    {
        return transpiler.appendToFile(target, file ->
        {
            file.printf("\t%s ", MCLKeywords.FUNC);
            file.print(identifier.value());
            file.print('(');
            for (int i = 0; i < parameters.size(); i++)
            {
                file.printf("%s %s", parameters.get(i).type, parameters.get(i).name);
                if (i < parameters.size() - 1) file.print(", ");
            }
            file.print(')');
            if (returnType != RuntimeType.VOID) file.printf(" %s %s", MCLKeywords.RETURN, returnType.toString());
            file.println();
        });
    }
}
