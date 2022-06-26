package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventSymbol extends Symbol
{
    public final List<VariableSymbol> parameters;
    public final List<Path> listenerFunctionFiles = new ArrayList<>();

    public EventSymbol(Token identifier, List<VariableSymbol> parameters)
    {
        super(identifier, SymbolType.EVENT);
        this.parameters = Collections.unmodifiableList(parameters);
    }

    @Override
    public MCLError transpileHeader(MCLTranspiler transpiler, Path target)
    {
        return transpiler.appendToFile(target, file ->
        {
            file.print("\tevent ");
            file.print(identifier.value());
            file.print('(');
            for (int i = 0; i < parameters.size(); i++)
            {
                file.printf("%s %s", parameters.get(i).type, parameters.get(i).name);
                if (i < parameters.size() - 1) file.print(", ");
            }
            file.println(')');
        });
    }
}
