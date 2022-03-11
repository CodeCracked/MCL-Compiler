package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.helpers.ParameterDeclarationList;
import mcl.compiler.syntax.helpers.TokenTypeLists;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNamedBlockNode extends AbstractBlockNode
{
    private Token identifier;
    private boolean unsafe;
    private ParameterDeclarationList parameters;
    private Token returnType;

    public AbstractNamedBlockNode(int indent, AbstractSyntaxNode parent, SyntaxAnalyzer syntax, boolean useChildSymbolTable) throws MCLSyntaxException, MCLSemanticException
    {
        super(indent, parent, syntax, useChildSymbolTable);
    }

    protected abstract List<TokenType> getSignatureFormat();

    @Override
    protected void constructSignatureNodes() throws MCLSyntaxException, MCLSemanticException
    {
        this.identifier = syntax.nextToken(TokenType.IDENTIFIER);

        List<TokenType> signatureFormat = getSignatureFormat();
        List<TokenType> clauseTypes = new ArrayList<>(signatureFormat);
        clauseTypes.add(TokenType.END_OF_LINE);

        Token nextToken = syntax.peekNextToken(clauseTypes);
        List<TokenType> existingClauseTypes = new ArrayList<>();

        int currentClauseIndex = -1;
        while (nextToken.getTokenType() != TokenType.END_OF_LINE)
        {
            TokenType clauseType = nextToken.getTokenType();
            int clauseIndex = signatureFormat.indexOf(clauseType);

            if (existingClauseTypes.contains(clauseType)) throw new MCLSemanticException(nextToken, "Duplicate '" + clauseType.getPrintableName() + "' clause found!");
            else if (clauseIndex > currentClauseIndex)
            {
                syntax.nextToken();

                switch(clauseType)
                {
                    case IS -> readIsClause(syntax);
                    case WITH -> readWithClause(syntax);
                    case RETURN -> readReturnClause(syntax);
                }

                existingClauseTypes.add(clauseType);
                currentClauseIndex = clauseIndex;
                nextToken = syntax.peekNextToken();
            }
            else
            {
                TokenType beforeClauseType = null;
                for (TokenType existingClause : existingClauseTypes)
                {
                    if (signatureFormat.indexOf(existingClause) > clauseIndex)
                    {
                        beforeClauseType = existingClause;
                        break;
                    }
                }
                throw new MCLSemanticException(nextToken, "'" + clauseType.getPrintableName() + "' clause must be before '" + beforeClauseType.getPrintableName() + "' clause!");
            }
        }
        syntax.nextToken(TokenType.END_OF_LINE);
    }

    private void readIsClause(SyntaxAnalyzer syntax) throws MCLSyntaxException
    {
        Token nextToken = syntax.nextToken(TokenTypeLists.MODIFIER_TOKEN_TYPES);
        if (nextToken.getTokenType() == TokenType.SAFE) unsafe = false;
        else if (nextToken.getTokenType() == TokenType.UNSAFE) unsafe = true;
    }
    private void readWithClause(SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        parameters = new ParameterDeclarationList(this, syntax, unsafe);
    }
    private void readReturnClause(SyntaxAnalyzer syntax) throws MCLSyntaxException
    {
        returnType = syntax.nextToken(TokenTypeLists.TYPE_DECLARATION_TOKEN_TYPES);
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
    public Token getReturnType()
    {
        return returnType;
    }
}
