package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

public class FunctionCallNode extends AbstractStatementNode
{
    private Token identifier;

    public FunctionCallNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax);
    }

    @Override
    public void readArguments() throws MCLSyntaxException, MCLSemanticException
    {
        this.identifier = syntax.nextToken(TokenType.IDENTIFIER);
        syntax.nextToken(TokenType.END_OF_LINE);
    }
}
