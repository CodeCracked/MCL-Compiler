package mcl.parser.nodes.natives;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;

public class NativeMethodDeclarationNode extends AbstractNode
{
    public final DataTypeNode returnType;
    public final IdentifierNode identifier;
    public final ParameterListNode parameters;
    public final Token nativeFunction;
    public final NativeBindListNode binds;
    
    private SymbolTable childTable;
    
    public NativeMethodDeclarationNode(Token keyword, DataTypeNode returnType, IdentifierNode identifier, ParameterListNode parameters, Token nativeFunction, NativeBindListNode binds)
    {
        super(keyword.start(), binds.end());
        this.returnType = returnType;
        this.identifier = identifier;
        this.parameters = parameters;
        this.nativeFunction = nativeFunction;
        this.binds = binds;
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Native " + identifier.value + "(binds " + nativeFunction.contents() + ")");
        return childTable;
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        return parameters.createParameterSymbols();
    }
}
