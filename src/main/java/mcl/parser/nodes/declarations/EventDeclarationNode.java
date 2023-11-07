package mcl.parser.nodes.declarations;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.DuplicateSymbolException;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.symbols.EventSymbol;

public class EventDeclarationNode extends AbstractFunctionDeclarationNode<EventSymbol>
{
    
    public EventDeclarationNode(Token keyword, IdentifierNode identifier, ParameterListNode parameters, Token semicolon)
    {
        super(keyword.start(), semicolon.end(), new FunctionSignatureNode(identifier.start(), parameters.end(), new DataTypeNode(keyword, MCLDataTypes.VOID), identifier, parameters));
        hasCodeGen = false;
    }
    
    @Override
    protected Result<EventSymbol> instantiateSymbol()
    {
        Result<EventSymbol> result = new Result<>();
        
        // Namespace Retrieval
        NamespaceNode namespace = result.register(findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
    
        // Symbol Creation
        return result.success(new EventSymbol(this, namespace.identifier.value, signature.identifier.value));
    }
}
