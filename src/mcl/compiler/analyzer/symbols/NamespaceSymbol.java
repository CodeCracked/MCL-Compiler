package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;

public class NamespaceSymbol extends Symbol
{
    public NamespaceSymbol(Token identifier)
    {
        super(identifier, SymbolType.NAMESPACE);
    }

    @Override
    public MCLError transpileHeader(MCLTranspiler transpiler, Path target)
    {
        return transpiler.appendToFile(target, file -> file.println("namespace " + identifier.value()));
    }
}
