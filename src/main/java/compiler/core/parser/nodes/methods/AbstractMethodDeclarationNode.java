package compiler.core.parser.nodes.methods;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;

public class AbstractMethodDeclarationNode extends AbstractNode
{
    public final MethodSignatureNode signature;
    
    protected SymbolTable childTable;
    
    public AbstractMethodDeclarationNode(SourcePosition start, SourcePosition end, MethodSignatureNode signature)
    {
        super(start, end);
        this.signature = signature;
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Method " + signature.identifier.value);
        return childTable;
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        return signature.parameters.createParameterSymbols();
    }
}
