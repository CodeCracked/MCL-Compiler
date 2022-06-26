package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.*;

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
            file.print(name);
            file.print('(');
            for (int i = 0; i < parameters.size(); i++)
            {
                file.printf("%s %s", parameters.get(i).type, parameters.get(i).name);
                if (i < parameters.size() - 1) file.print(", ");
            }
            file.println(')');
        });
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventSymbol that = (EventSymbol) o;
        if (!Objects.equals(name, that.name)) return false;
        else if (parameters.size() != that.parameters.size()) return false;
        else
        {
            for (int i = 0; i < parameters.size(); i++) if (!Objects.equals(parameters.get(i).type, that.parameters.get(i).type)) return false;
            return true;
        }
    }

    @Override
    public int hashCode()
    {
        Object[] objs = new Object[parameters.size() + 1];
        objs[0] = name;
        for (int i = 1; i < objs.length; i++) objs[i] = parameters.get(i - 1).type;
        return Arrays.hashCode(objs);
    }
}
