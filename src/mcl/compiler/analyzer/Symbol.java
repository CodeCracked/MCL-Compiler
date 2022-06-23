package mcl.compiler.analyzer;

import mcl.compiler.lexer.Token;

public class Symbol
{
    public final Token identifier;
    public final String name;
    public final SymbolType symbolType;

    public Symbol(Token identifier, SymbolType symbolType)
    {
        this.identifier = identifier;
        this.name = (String)this.identifier.value();
        this.symbolType = symbolType;
    }
}
