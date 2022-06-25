package mcl.compiler.parser.rules.expressions;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;

import java.util.Set;

public class ArithmeticExpressionRule implements GrammarRule
{
    private final Set<Token> arithmeticOperators = Token.descriptions(TokenType.PLUS, TokenType.MINUS);

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        AbstractNode node = result.register(GrammarRules.binaryOperationRule(parser, GrammarRules.FACTOR, arithmeticOperators));
        if (result.error() != null) return result.failure(new MCLSyntaxError(parser, "Expected variable type, number, identifier, operation, or '('"));

        return result.success(node);
    }
}
