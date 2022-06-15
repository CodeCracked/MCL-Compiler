package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.helpers.TokenTypeLists;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

public class ReturnStatementNode extends AbstractStatementNode
{
    private Token value;

    public ReturnStatementNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax);
    }

    @Override
    public void readArguments() throws MCLSyntaxException
    {
        value = syntax.nextToken(TokenTypeLists.VALUE_TOKEN_TYPES);
        syntax.nextToken(TokenType.END_OF_LINE);
    }

    @Override
    public String debugDetails()
    {
        return value.getToken();
    }
}
