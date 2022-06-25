package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

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
}
