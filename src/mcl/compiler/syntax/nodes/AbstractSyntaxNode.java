package mcl.compiler.syntax.nodes;

import mcl.compiler.syntax.SymbolTable;
import mcl.compiler.syntax.SyntaxAnalyzer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSyntaxNode
{
    private final List<AbstractSyntaxNode> children = new ArrayList<>();

    protected AbstractSyntaxNode parent;
    protected int indexInParent = -1;
    protected final SyntaxAnalyzer syntax;
    protected SymbolTable symbolTable;

    public AbstractSyntaxNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax, SymbolTable symbolTable)
    {
        this.parent = parent;
        this.syntax = syntax;
        this.symbolTable = symbolTable;
    }

    public <T extends AbstractSyntaxNode> T getParent() { return (T)parent; }
    public int getIndexInParent() { return indexInParent; }
    public SymbolTable getSymbolTable() { return symbolTable; }

    public void addChild(AbstractSyntaxNode node)
    {
        children.add(node);
        if (node.parent != this) throw new IllegalArgumentException("Node " + node.getClass().getName() + " has different parent!");
        node.indexInParent = children.size() - 1;
    }
}
