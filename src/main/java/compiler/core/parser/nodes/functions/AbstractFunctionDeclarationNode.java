package compiler.core.parser.nodes.functions;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;

public class AbstractFunctionDeclarationNode extends AbstractNode
{
    public final FunctionSignatureNode signature;
    
    protected SymbolTable childTable;
    
    public AbstractFunctionDeclarationNode(SourcePosition start, SourcePosition end, FunctionSignatureNode signature)
    {
        super(start, end);
        this.signature = signature;
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Function " + signature.identifier.value);
        return childTable;
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        return signature.parameters.createParameterSymbols();
    }
}
