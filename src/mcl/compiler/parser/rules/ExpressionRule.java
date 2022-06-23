package mcl.compiler.parser.rules;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;

import java.util.Set;

public final class ExpressionRule implements GrammarRule
{
    private final Set<Token> comparisonOperators = Set.of(Token.description(TokenType.KEYWORD, MCLKeywords.AND), Token.description(TokenType.KEYWORD, MCLKeywords.OR));

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        AbstractNode node = result.register(GrammarRules.binaryOperationRule(parser, GrammarRules.COMPARISON_EXPRESSION, comparisonOperators));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser, "Expected number, identifier, operation, or '('"));

        return result.success(node);
    }
}
