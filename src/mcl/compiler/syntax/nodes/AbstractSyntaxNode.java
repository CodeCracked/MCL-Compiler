package mcl.compiler.syntax.nodes;

import mcl.compiler.syntax.SymbolTable;
import mcl.compiler.syntax.SyntaxAnalyzer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    public void debugPrint(PrintStream out) { debugPrint(out, 0); }
    public void debugPrint(PrintStream out, int depth)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) sb.append("    ");
        sb.append(this);
        out.println(sb);

        forEachChild(child -> child.debugPrint(out, depth + 1));
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
    public void forEachChild(Consumer<AbstractSyntaxNode> consumer)
    {
        children.forEach(consumer);
    }

    public String debugDetails() { return null; }
    @Override
    public String toString()
    {
        String debugDetails = debugDetails();
        if (debugDetails == null) return getClass().getSimpleName();
        else return getClass().getSimpleName() + "[" + debugDetails + "]";
    }
}
