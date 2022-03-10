package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SymbolTable;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.ParseTree;

import java.util.List;

public class NamespaceNode extends AbstractSyntaxNode
{
    public static final List<TokenType> EXPECTED_TOKEN_TYPES = List.of(TokenType.FUNCTION);

    private String namespace;

    public NamespaceNode(ParseTree parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, parent.getNamespaceSymbolTable(syntax.peekNextToken(TokenType.IDENTIFIER).getToken()));
        this.namespace = syntax.nextToken(TokenType.IDENTIFIER).getToken();
        syntax.nextToken(TokenType.END_OF_LINE);

        while (syntax.hasNextToken() && syntax.peekIndent() == 1)
        {
            syntax.popIndent();
            Token nextToken = syntax.nextToken(EXPECTED_TOKEN_TYPES);
            switch (nextToken.getTokenType())
            {
                case FUNCTION -> addChild(new FunctionNode(this, syntax));
            }
        }
    }

    public String getNamespace() { return namespace; }
}
