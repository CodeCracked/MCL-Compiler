package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

import java.util.List;

public abstract class AbstractBlockNode extends AbstractSyntaxNode
{
    private final int indent;

    public AbstractBlockNode(int indent, AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, parent.getSymbolTable().createChildTable());
        this.indent = indent;
        constructSignatureNodes(syntax);
        constructBodyNodes(syntax);
    }

    protected abstract List<TokenType> getBodyTokenTypes();
    protected abstract void processBodyToken(Token token, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException;

    protected void constructSignatureNodes(SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        syntax.nextToken(TokenType.END_OF_LINE);
    }
    protected void constructBodyNodes(SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        List<TokenType> bodyTokenTypes = getBodyTokenTypes();
        Token nextToken;

        while (syntax.hasNextToken() && syntax.peekIndent() == indent)
        {
            syntax.popIndent();
            nextToken = syntax.nextToken(bodyTokenTypes);
            processBodyToken(nextToken, syntax);
        }
    }

    public int getIndent()
    {
        return indent;
    }
}
