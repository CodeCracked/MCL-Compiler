package mcl.compiler.parser;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.nodes.*;
import mcl.compiler.source.MCLSourceCollection;

import java.util.List;
import java.util.Set;

public class MCLParser
{
    private final MCLSourceCollection source;
    private final List<Token> tokens;
    private int tokenIndex;
    private Token currentToken;

    public MCLParser(MCLSourceCollection source, List<Token> tokens)
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
    public Token getCurrentToken() { return currentToken; }

    //region Grammar Rules
    private static ParseResult atomRule(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        Token token = parser.getCurrentToken();

        if (token.type() == TokenType.PLUS || token.type() == TokenType.MINUS)
        {
            result.registerAdvancement();
            parser.advance();
            AbstractNode factor = result.register(atomRule(parser));
            if (result.error() != null) return result;
            else return result.success(new UnaryOpNode(token, factor));
        }

        else if (token.type() == TokenType.IDENTIFIER)
        {
            result.registerAdvancement();
            parser.advance();
            return result.success(new VarAccessNode(token));
        }

        else if (token.type() == TokenType.INT || token.type() == TokenType.FLOAT)
        {
            result.registerAdvancement();
            parser.advance();
            return result.success(new NumberNode(token));
        }

        else if (token.type() == TokenType.LPAREN)
        {
            result.registerAdvancement();
            parser.advance();
            AbstractNode expression = result.register(expressionRule(parser));
            if (result.error() != null) return result;
            if (parser.getCurrentToken().type() == TokenType.RPAREN)
            {
                result.registerAdvancement();
                parser.advance();
                return result.success(expression);
            }
            else return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected ')'"));
        }

        return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected int, float, identifier, '+', '-', or '('"));
    }
    private static ParseResult factorRule(MCLParser parser)
    {
        return binaryOperationRule(parser, MCLParser::atomRule, Set.of(TokenType.MUL, TokenType.DIV));
    }
    private static ParseResult expressionRule(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        if (parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.INT_KEYWORD) || parser.getCurrentToken().matches(TokenType.KEYWORD, MCLKeywords.FLOAT_KEYWORD))
        {
            Token type = parser.getCurrentToken();

            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.IDENTIFIER) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected identifier"));

            Token identifier = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();

            if (parser.getCurrentToken().type() != TokenType.EQUALS) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected '='"));

            result.registerAdvancement();
            parser.advance();
            AbstractNode valueExpression = result.register(expressionRule(parser));
            if (result.error() != null) return result;
            else return result.success(new VarAssignNode(type, identifier, valueExpression));
        }

        AbstractNode node = result.register(binaryOperationRule(parser, MCLParser::factorRule, Set.of(TokenType.PLUS, TokenType.MINUS)));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser.getSource(), parser.getCurrentToken(), "Expected 'int', 'float', int, float, identifier, '+', '-', or '('"));

        return result.success(node);
    }
    private static ParseResult binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<TokenType> operations)
    {
        ParseResult result = new ParseResult();
        AbstractNode left = result.register(argumentRule.build(parser));
        if (result.error() != null) return result;

        while (parser.getCurrentToken() != null && operations.contains(parser.getCurrentToken().type()))
        {
            Token operation = parser.getCurrentToken();
            result.registerAdvancement();
            parser.advance();
            AbstractNode right = result.register(argumentRule.build(parser));
            if (result.error() != null) return result;

            left = new BinaryOpNode(left, operation, right);
        }

        return result.success(left);
    }
    //endregion
}
