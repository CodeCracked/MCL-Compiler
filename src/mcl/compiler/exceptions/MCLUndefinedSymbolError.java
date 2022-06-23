package mcl.compiler.exceptions;

import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

public class MCLUndefinedSymbolError extends MCLError
{
    public MCLUndefinedSymbolError(MCLSourceCollection source, Token identifier, SymbolType symbolType)
    {
        super(source.getCodeLocation(identifier.startPosition()), source.getCodeLocation(identifier.endPosition()), "Undefined Symbol", String.format("%s '%s'", symbolType.name().toLowerCase(), identifier.value()));
    }
}