package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;

public class VariableSymbol extends Symbol
{
    public final Token type;

    public VariableSymbol(Token identifier, Token typeKeyword)
    {
        super(identifier, SymbolType.VARIABLE);
        this.type = typeKeyword;
    }
}
