package mcl.compiler.syntax;

import mcl.compiler.exceptions.MCLDuplicateSymbolException;
import mcl.compiler.lexer.Token;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable
{
    public record Symbol(Token token, SymbolType type, AbstractSyntaxNode node)
    {
        public Token getToken()
        {
            return token;
        }

        public SymbolType getType()
        {
            return type;
        }

        public AbstractSyntaxNode getNode()
        {
            return node;
        }
    }

    private SymbolTable parent;
    private List<SymbolTable> children = new ArrayList<>();
    private final Map<String, Symbol> symbolTable = new HashMap<>();

    public SymbolTable createChildTable()
    {
        SymbolTable child = new SymbolTable();
        child.parent = this;
        children.add(child);
        return child;
    }

    public boolean containsSymbol(String name)
    {
        if (symbolTable.containsKey(name)) return true;
        else if (parent != null) return parent.containsSymbol(name);
        else return false;
    }
    public void addSymbol(Token symbolIdentifier, SymbolType symbolType, AbstractSyntaxNode node) throws MCLDuplicateSymbolException
    {
        if (containsSymbol(symbolIdentifier.getToken())) throw new MCLDuplicateSymbolException(symbolIdentifier, symbolTable.get(symbolIdentifier.getToken()));
        symbolTable.put(symbolIdentifier.getToken(), new Symbol(symbolIdentifier, symbolType, node));
    }
    public Symbol getSymbol(String name)
    {
        if (symbolTable.containsKey(name)) return symbolTable.get(name);
        else if (parent != null) return parent.getSymbol(name);
        else return null;
    }
}
