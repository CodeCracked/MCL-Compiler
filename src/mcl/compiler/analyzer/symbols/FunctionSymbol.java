package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

public class FunctionSymbol extends Symbol
{
    public final RuntimeType returnType;

    public FunctionSymbol(Token identifier, RuntimeType returnType)
    {
        super(identifier, SymbolType.FUNCTION);
        this.returnType = returnType;
    }
}
