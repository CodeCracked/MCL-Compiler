package mcl.compiler.parser;

import mcl.compiler.MCLCompiler;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.nodes.BinOpNode;
import mcl.compiler.parser.nodes.NumberNode;

import java.util.List;
import java.util.Set;

public class MCLParser
{
    private final MCLCompiler compiler;
    private final List<Token<?>> tokens;
    private int tokenIndex;
    private Token<?> currentToken;

    public MCLParser(MCLCompiler compiler, List<Token<?>> tokens)
    {
        this.compiler = compiler;
        this.tokens = tokens;
        this.tokenIndex = -1;
        advance();
    }

    public void advance()
    {
        this.tokenIndex++;
        this.currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : null;
    }
    public AbstractNode parse()
    {
        return expressionRule(this);
    }

    public MCLSourceCollection getSource() { return compiler.getSource(); }
    public Token<?> getCurrentToken() { return currentToken; }

    //region Grammar Rules
    private static AbstractNode factorRule(MCLParser parser)
    {
        Token<?> token = parser.getCurrentToken();
        if (token != null && (token.type() == TokenType.INT || token.type() == TokenType.FLOAT))
        {
            parser.advance();
            return new NumberNode(token);
        }
        else return null;
    }
    private static AbstractNode termRule(MCLParser parser)
    {
        return binaryOperationRule(parser, MCLParser::factorRule, Set.of(TokenType.MUL, TokenType.DIV));
    }
    private static AbstractNode expressionRule(MCLParser parser)
    {
        return binaryOperationRule(parser, MCLParser::termRule, Set.of(TokenType.PLUS, TokenType.MINUS));
    }
    private static AbstractNode binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<TokenType> operations)
    {
        AbstractNode left = argumentRule.build(parser);

        while (parser.getCurrentToken() != null && operations.contains(parser.getCurrentToken().type()))
        {
            Token<?> operation = parser.getCurrentToken();
            parser.advance();
            AbstractNode right = argumentRule.build(parser);

            left = new BinOpNode(left, operation, right);
        }

        return left;
    }
    //endregion
}
