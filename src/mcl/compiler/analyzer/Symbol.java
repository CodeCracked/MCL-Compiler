package mcl.compiler.analyzer;

import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;

public abstract class Symbol
{
    public final Token identifier;
    public final String name;
    public final SymbolType symbolType;

    public String tableLocation;

    public Symbol(Token identifier, SymbolType symbolType)
    {
        this.identifier = identifier;
        this.name = (String)this.identifier.value();
        this.symbolType = symbolType;
    }

    public abstract MCLError transpileHeader(MCLTranspiler transpiler, Path target);
}
