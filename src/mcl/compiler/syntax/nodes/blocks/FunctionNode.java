package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SymbolType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.statements.PrintNode;

import java.util.List;

public class FunctionNode extends AbstractNamedBlockNode
{
    public static final List<TokenType> SIGNATURE_FORMAT = List.of(TokenType.IS, TokenType.WITH, TokenType.RETURN);
    public static final List<TokenType> BODY_TOKEN_TYPES = List.of(TokenType.PRINT);

    public FunctionNode(NamespaceNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(2, parent, syntax);
        this.parent.getSymbolTable().addSymbol(getIdentifier(), SymbolType.FUNCTION, this);
    }

    @Override
    protected List<TokenType> getSignatureFormat()
    {
        return SIGNATURE_FORMAT;
    }
    @Override
    protected List<TokenType> getBodyTokenTypes()
    {
        return BODY_TOKEN_TYPES;
    }
    @Override
    protected void processBodyToken(Token token, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        switch (token.getTokenType())
        {
            case PRINT -> addChild(new PrintNode(this, syntax));
        }
    }
}
