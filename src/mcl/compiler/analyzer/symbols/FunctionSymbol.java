package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

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
}
