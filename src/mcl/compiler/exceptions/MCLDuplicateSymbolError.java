package mcl.compiler.exceptions;

import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

public class MCLDuplicateSymbolError extends MCLError
{
    public MCLDuplicateSymbolError(MCLSourceCollection source, Token identifier, SymbolType symbolType)
    {
        super(source.getCodeLocation(identifier.startPosition()), source.getCodeLocation(identifier.endPosition()), "Duplicate Symbol", String.format("%s '%s' already defined at this scope!", symbolType.name().toLowerCase(), identifier.value()));
    }
}