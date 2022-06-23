package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

public class VariableSymbol extends Symbol
{
    public final RuntimeType type;

    public VariableSymbol(Token identifier, RuntimeType type)
    {
        super(identifier, SymbolType.VARIABLE);
        this.type = type;
    }
}
