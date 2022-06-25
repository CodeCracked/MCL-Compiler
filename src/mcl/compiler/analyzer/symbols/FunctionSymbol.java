package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

import java.util.Collections;
import java.util.List;

public class FunctionSymbol extends Symbol
{
    public final List<RuntimeType> parameters;
    public final RuntimeType returnType;

    public FunctionSymbol(Token identifier, List<RuntimeType> parameters, RuntimeType returnType)
    {
        super(identifier, SymbolType.FUNCTION);
        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType;
    }
}
