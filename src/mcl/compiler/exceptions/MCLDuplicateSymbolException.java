package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;
import mcl.compiler.syntax.SymbolTable;

public class MCLDuplicateSymbolException extends MCLSemanticException
{
    public MCLDuplicateSymbolException(Token redefinition, SymbolTable.Symbol existingSymbol)
    {
        super(redefinition.getLocation() + " Symbol '" + redefinition.getToken() + "' already defined at " + existingSymbol.getToken().getLocation() + "!");
    }
}
