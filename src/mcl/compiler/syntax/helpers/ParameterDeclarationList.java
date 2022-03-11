package mcl.compiler.syntax.helpers;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SymbolTable;
import mcl.compiler.syntax.SymbolType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterDeclarationList
{
    public static class ParameterDeclaration
    {
        private final Token type;
        private final String name;
        private final boolean unsafe;

        public ParameterDeclaration(Token type, Token identifier, boolean unsafe)
        {
            this.type = type;
            this.name = identifier.getToken();
            this.unsafe = unsafe;
        }

        public Token getType()
        {
            return type;
        }

        public String getName()
        {
            return name;
        }

        public boolean isUnsafe()
        {
            return unsafe;
        }
    }

    public final List<ParameterDeclaration> parameters;

    public ParameterDeclarationList(AbstractSyntaxNode owner, SyntaxAnalyzer syntax, boolean defaultUnsafe) throws MCLSyntaxException, MCLSemanticException
    {
        List<ParameterDeclaration> parameters = new ArrayList<>();

        readParameterDeclarations:
        while (true)
        {
            Token typeToken = syntax.nextToken(TokenTypeLists.TYPE_DECLARATION_TOKEN_TYPES);
            Token identifierToken = syntax.nextToken(TokenType.IDENTIFIER);
            boolean unsafe = defaultUnsafe;
            boolean processedIfClause = false;

            Token nextToken;
            while (true)
            {
                nextToken = syntax.peekNextToken();

                if (nextToken.getTokenType() == TokenType.IS)
                {
                    if (processedIfClause) throw new MCLSyntaxException(nextToken, "Duplicate 'is' clause found!");
                    processedIfClause = true;
                    syntax.nextToken();

                    nextToken = syntax.nextToken(TokenTypeLists.MODIFIER_TOKEN_TYPES);
                    if (nextToken.getTokenType() == TokenType.SAFE) unsafe = false;
                    else if (nextToken.getTokenType() == TokenType.UNSAFE) unsafe = true;
                }
                else
                {
                    parameters.add(new ParameterDeclaration(typeToken, identifierToken, unsafe));
                    owner.getSymbolTable().addSymbol(identifierToken, SymbolType.PARAMETER, owner);

                    if (nextToken.getTokenType() == TokenType.SEPARATOR)
                    {
                        syntax.nextToken();
                        break;
                    }
                    else break readParameterDeclarations;
                }
            }
        }

        this.parameters = Collections.unmodifiableList(parameters);
    }
}
