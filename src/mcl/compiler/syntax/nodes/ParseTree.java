package mcl.compiler.syntax.nodes;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SymbolTable;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.blocks.NamespaceNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseTree extends AbstractSyntaxNode
{
    public static final List<TokenType> EXPECTED_TOKEN_TYPES = List.of(TokenType.NAMESPACE);

    private final Map<String, SymbolTable> namespaceSymbolTables = new HashMap<>();

    public ParseTree(SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(null, syntax, null);

        while(syntax.hasNextToken())
        {
            Token token = syntax.nextToken(EXPECTED_TOKEN_TYPES);
            if (token.getTokenType() == TokenType.NAMESPACE) addChild(new NamespaceNode(this, syntax));
        }
    }

    public SymbolTable getNamespaceSymbolTable(String namespace)
    {
        if (!namespaceSymbolTables.containsKey(namespace)) namespaceSymbolTables.put(namespace, new SymbolTable());
        return namespaceSymbolTables.get(namespace);
    }
}
