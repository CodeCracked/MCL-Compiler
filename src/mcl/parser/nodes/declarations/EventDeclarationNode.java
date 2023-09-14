package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.DuplicateSymbolException;
import mcl.parser.symbols.EventSymbol;

public class EventDeclarationNode extends AbstractNode
{
    public final IdentifierNode identifier;
    public final ParameterListNode parameterList;
    
    public EventDeclarationNode(Token keyword, IdentifierNode identifier, ParameterListNode parameterList, Token semicolon)
    {
        super(keyword.start(), semicolon.end(), false);
        this.identifier = identifier;
        this.parameterList = parameterList;
    }
    
    @Override
    public Result<Void> createSymbols()
    {
        Result<Void> result = new Result<>();
        
        // Symbol Creation
        EventSymbol symbol = new EventSymbol(identifier.value);
        if (!symbolTable().addSymbolWithUniqueName(symbol, true)) return result.failure(new DuplicateSymbolException(identifier, symbol));
        
        return result.success(null);
    }
}
