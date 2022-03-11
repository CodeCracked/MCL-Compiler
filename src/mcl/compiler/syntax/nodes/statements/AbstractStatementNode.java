package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

public abstract class AbstractStatementNode extends AbstractSyntaxNode
{
    public AbstractStatementNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax, parent.getSymbolTable());
        init();
        readArguments();
    }

    public void init() {}
    public abstract void readArguments() throws MCLSyntaxException, MCLSemanticException;
}
