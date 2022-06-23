package mcl.compiler.parser;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.nodes.BinOpNode;
import mcl.compiler.parser.nodes.NumberNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.List;
import java.util.Set;

public class MCLParser
{
    private final MCLSourceCollection source;
    private final List<Token<?>> tokens;
    private int tokenIndex;
    private Token<?> currentToken;

    public MCLParser(MCLSourceCollection source, List<Token<?>> tokens)
    {
        this.source = source;
        this.tokens = tokens;
        this.tokenIndex = -1;
        advance();
    }

    public AbstractNode advance()
    {
        this.tokenIndex++;
        this.currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : null;
        return null;
    }
    public ParseResult parse()
    {
        ParseResult result = expressionRule(this);
        if (result.error() == null && currentToken.type() != TokenType.EOF) return result.failure(new MCLSyntaxError(source.getCodeLocation(currentToken.startPosition()),
                source.getCodeLocation(currentToken.endPosition()), "Expected '+', '-', '*', or '/'"));
        return result;
    }

    public MCLSourceCollection getSource() { return source; }
    public Token<?> getCurrentToken() { return currentToken; }

    //region Grammar Rules
    private static ParseResult factorRule(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        Token<?> token = parser.getCurrentToken();
        if (token.type() == TokenType.INT || token.type() == TokenType.FLOAT)
        {
            result.register(parser.advance());
            return result.success(new NumberNode(token));
        }
        else return result.failure(new MCLSyntaxError(parser.getSource().getCodeLocation(token.startPosition()), parser.getSource().getCodeLocation(token.endPosition()), "Expected int or float"));
    }
    private static ParseResult termRule(MCLParser parser)
    {
        return binaryOperationRule(parser, MCLParser::factorRule, Set.of(TokenType.MUL, TokenType.DIV));
    }
    private static ParseResult expressionRule(MCLParser parser)
    {
        return binaryOperationRule(parser, MCLParser::termRule, Set.of(TokenType.PLUS, TokenType.MINUS));
    }
    private static ParseResult binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<TokenType> operations)
    {
        ParseResult result = new ParseResult();
        AbstractNode left = result.register(argumentRule.build(parser));
        if (result.error() != null) return result;

        while (parser.getCurrentToken() != null && operations.contains(parser.getCurrentToken().type()))
        {
            Token<?> operation = parser.getCurrentToken();
            result.register(parser.advance());
            AbstractNode right = result.register(argumentRule.build(parser));
            if (result.error() != null) return result;

            left = new BinOpNode(left, operation, right);
        }

        return result.success(left);
    }
    //endregion
}
