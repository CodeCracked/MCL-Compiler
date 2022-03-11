package mcl.compiler.syntax;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.nodes.ParseTree;

import java.util.Collection;
import java.util.List;

public class SyntaxAnalyzer
{
    private int index;
    private List<Token> tokens;

    public ParseTree constructParseTree(List<Token> tokens) throws MCLSyntaxException, MCLSemanticException
    {
        this.index = 0;
        this.tokens = tokens;

        ParseTree parseTree = new ParseTree(this);
        System.out.println("Parse Tree:");
        parseTree.debugPrint(System.out);
        System.out.println();
        return parseTree;
    }

    public boolean hasNextToken()
    {
        return index < tokens.size();
    }

    public Token currentToken()
    {
        return tokens.get(index - 1);
    }
    public Token currentToken(Collection<TokenType> expected) throws MCLSyntaxException
    {
        Token currentToken = currentToken();
        if (!expected.contains(currentToken.getTokenType())) throw new MCLSyntaxException(expected, currentToken);
        else return currentToken;
    }
    public Token currentToken(TokenType expected) throws MCLSyntaxException
    {
        Token currentToken = currentToken();
        if (expected != currentToken.getTokenType()) throw new MCLSyntaxException(expected, currentToken);
        else return currentToken;
    }

    public Token nextToken()
    {
        index++;
        return tokens.get(index - 1);
    }
    public Token nextToken(Collection<TokenType> expected) throws MCLSyntaxException
    {
        Token nextToken = nextToken();
        if (!expected.contains(nextToken.getTokenType())) throw new MCLSyntaxException(expected, nextToken);
        else return nextToken;
    }
    public Token nextToken(TokenType expected) throws MCLSyntaxException
    {
        Token nextToken = nextToken();
        if (expected != nextToken.getTokenType()) throw new MCLSyntaxException(expected, nextToken);
        else return nextToken;
    }

    public Token peekNextToken()
    {
        return tokens.get(index);
    }
    public Token peekNextToken(Collection<TokenType> expected) throws MCLSyntaxException
    {
        Token nextToken = peekNextToken();
        if (!expected.contains(nextToken.getTokenType())) throw new MCLSyntaxException(expected, nextToken);
        else return nextToken;
    }
    public Token peekNextToken(TokenType expected) throws MCLSyntaxException
    {
        Token nextToken = peekNextToken();
        if (expected != nextToken.getTokenType()) throw new MCLSyntaxException(expected, nextToken);
        else return nextToken;
    }

    public int peekIndent()
    {
        int i = 0;
        while (tokens.get(index + i).getTokenType() == TokenType.INDENT) i++;
        return i;
    }
    public int popIndent()
    {
        int i = 0;
        while (peekNextToken().getTokenType() == TokenType.INDENT)
        {
            nextToken();
            i++;
        }
        return i;
    }
}
