package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

public class EventSymbol extends Symbol
{
    public EventSymbol(Token identifier)
    {
        super(identifier, SymbolType.VARIABLE);
    }
}
