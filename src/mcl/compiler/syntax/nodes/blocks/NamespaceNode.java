package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.ParseTree;
import mcl.compiler.syntax.nodes.definitions.EventDefinitionNode;

import java.util.List;

public class NamespaceNode extends AbstractBlockNode
{
    public static final List<TokenType> BODY_TOKEN_TYPES = List.of(TokenType.EVENT, TokenType.LISTENER, TokenType.FUNCTION);

    private Token namespace;

    public NamespaceNode(ParseTree parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(1, parent, syntax, false);
    }

    @Override
    protected void constructSignatureNodes() throws MCLSyntaxException, MCLSemanticException
    {
        this.namespace = syntax.nextToken(TokenType.IDENTIFIER);
        this.symbolTable = ((ParseTree)parent).getNamespaceSymbolTable(this.namespace.getToken());
        super.constructSignatureNodes();
    }
    @Override
    protected List<TokenType> getBodyTokenTypes()
    {
        return BODY_TOKEN_TYPES;
    }
    @Override
    protected void processBodyToken(Token token) throws MCLSyntaxException, MCLSemanticException
    {
        switch (token.getTokenType())
        {
            case EVENT -> addChild(new EventDefinitionNode(this, syntax));
            case LISTENER -> addChild(new ListenerDefinitionNode(this, syntax));
            case FUNCTION -> addChild(new FunctionDefinitionNode(this, syntax));
        }
    }

    @Override
    public String debugDetails()
    {
        return namespace.getToken();
    }

    public Token getNamespace()
    {
        return namespace;
    }
}
