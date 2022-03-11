package mcl.compiler.syntax.nodes.definitions;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.helpers.ParameterDeclarationList;
import mcl.compiler.syntax.helpers.TokenTypeLists;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

public class EventDefinitionNode extends AbstractSyntaxNode
{
    private Token identifier;
    private boolean unsafe;
    private ParameterDeclarationList parameters;

    public EventDefinitionNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax, parent.getSymbolTable());
        this.identifier = syntax.nextToken(TokenType.IDENTIFIER);

        Token nextToken = syntax.nextToken();
        if (nextToken.getTokenType() == TokenType.IS)
        {
            nextToken = syntax.nextToken(TokenTypeLists.MODIFIER_TOKEN_TYPES);
            if (nextToken.getTokenType() == TokenType.UNSAFE) unsafe = true;
            else unsafe = false;
            nextToken = syntax.nextToken();
        }
        if (nextToken.getTokenType() == TokenType.WITH)
        {
            parameters = new ParameterDeclarationList(this, syntax, unsafe);
            syntax.nextToken();
        }

        syntax.currentToken(TokenType.END_OF_LINE);
    }

    public Token getIdentifier()
    {
        return identifier;
    }
    public boolean isUnsafe()
    {
        return unsafe;
    }
    public ParameterDeclarationList getParameters()
    {
        return parameters;
    }

    @Override
    public String debugDetails()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.identifier.getToken());

        if (unsafe) sb.append(", unsafe");
        else sb.append(", safe");

        if (parameters != null)
        {
            sb.append(", ");
            sb.append(parameters);
        }

        return sb.toString();
    }
}
