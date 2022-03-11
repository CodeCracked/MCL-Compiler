package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.helpers.ParameterValueList;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

import java.util.List;

public class FunctionCallNode extends AbstractStatementNode
{
    public static final List<TokenType> CLAUSE_TOKEN_TYPES = List.of(TokenType.WITH, TokenType.END_OF_LINE);

    private Token identifier;
    private ParameterValueList parameterValues;

    public FunctionCallNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax);
    }

    @Override
    public void readArguments() throws MCLSyntaxException
    {
        this.identifier = syntax.nextToken(TokenType.IDENTIFIER);

        Token nextToken = syntax.nextToken(CLAUSE_TOKEN_TYPES);
        if (nextToken.getTokenType() == TokenType.WITH) this.parameterValues = new ParameterValueList(syntax);
    }

    @Override
    public String debugDetails()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.identifier.getToken());
        if (this.parameterValues != null)
        {
            sb.append(", ");
            sb.append(this.parameterValues);
        }
        return sb.toString();
    }
}
