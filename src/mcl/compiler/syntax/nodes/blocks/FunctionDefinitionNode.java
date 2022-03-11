package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SymbolType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.statements.FunctionCallNode;
import mcl.compiler.syntax.nodes.statements.PrintNode;
import mcl.compiler.syntax.nodes.statements.TriggerEventNode;

import java.util.List;

public class FunctionDefinitionNode extends AbstractNamedBlockNode
{
    public static final List<TokenType> SIGNATURE_FORMAT = List.of(TokenType.IS, TokenType.WITH, TokenType.RETURN);
    public static final List<TokenType> BODY_TOKEN_TYPES = List.of(TokenType.CALL, TokenType.TRIGGER, TokenType.PRINT);

    public FunctionDefinitionNode(NamespaceNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(2, parent, syntax, true);
        if (getClass().equals(FunctionDefinitionNode.class)) this.parent.getSymbolTable().addSymbol(getIdentifier(), SymbolType.FUNCTION, this);
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
    protected void processBodyToken(Token token) throws MCLSyntaxException, MCLSemanticException
    {
        switch (token.getTokenType())
        {
            case CALL -> addChild(new FunctionCallNode(this, syntax));
            case TRIGGER -> addChild(new TriggerEventNode(this, syntax));
            case PRINT -> addChild(new PrintNode(this, syntax));
        }
    }
}
