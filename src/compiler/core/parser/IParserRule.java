package compiler.core.parser;

import compiler.core.lexer.Token;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface IParserRule
{
    default Result<Token> tokenType(Parser parser, Enum<?> type, String expected)
    {
        return token(parser, token -> token.type() == type, () -> UnexpectedTokenException.expected(parser, expected));
    }
    default Result<Token> token(Parser parser, Predicate<Token> predicate, Supplier<Exception> failureBuilder)
    {
        Result<Token> result = new Result<>();
        
        Token token = parser.getCurrentToken();
        if (!predicate.test(token)) return result.failure(failureBuilder.get());
        parser.advance();
        result.registerAdvancement();
        
        return result.success(token);
    }
}
