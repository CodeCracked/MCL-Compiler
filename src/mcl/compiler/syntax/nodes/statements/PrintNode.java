package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;
import mcl.compiler.syntax.SyntaxAnalyzer;

import java.util.List;

public class PrintNode extends AbstractSyntaxNode
{
    public static final List<TokenType> MESSAGE_TOKEN_TYPES = List.of(TokenType.INT_LITERAL, TokenType.FLOAT_LITERAL, TokenType.STRING_LITERAL, TokenType.IDENTIFIER);

    private Token message;

    public PrintNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException
    {
        super(parent, parent.getSymbolTable());
        this.message = syntax.nextToken(MESSAGE_TOKEN_TYPES);
        syntax.nextToken(TokenType.END_OF_LINE);
    }

    public Token getMessage()
    {
        return message;
    }
}
