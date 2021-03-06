package mcl.compiler.analyzer.symbols;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;

public class VariableSymbol extends Symbol
{
    public final RuntimeType type;

    public VariableSymbol(Token identifier, RuntimeType type)
    {
        super(identifier, SymbolType.VARIABLE);
        this.type = type;
    }

    @Override
    public MCLError transpileHeader(MCLTranspiler transpiler, Path target)
    {
        return null;
    }
}
