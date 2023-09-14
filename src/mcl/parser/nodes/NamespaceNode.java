package mcl.parser.nodes;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.symbols.SymbolTable;

public class NamespaceNode extends AbstractNode
{
    public final IdentifierNode identifier;
    public final BlockNode body;
    
    public NamespaceNode(Token keyword, IdentifierNode identifier, BlockNode body)
    {
        super(keyword.start(), body.end());
        
        this.identifier = identifier;
        this.body = body;
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (child.equals(body)) return symbolTable().getOrCreateChildTable(identifier.value);
        else return symbolTable();
    }
}
