package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;
import mcl.compiler.source.MCLSourceCollection;

public class MCLUndefinedNamespaceError extends MCLError
{
    public MCLUndefinedNamespaceError(MCLSourceCollection source, Token identifier)
    {
        super(source.getCodeLocation(identifier.startPosition()), source.getCodeLocation(identifier.endPosition()), "Undefined Namespace", (String)identifier.value());
    }
}