package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.TokenBuilderList;
import compiler.core.lexer.types.MathTokenType;

public class MathSymbolsTokenBuilder extends TokenBuilderList
{
    public MathSymbolsTokenBuilder(AbstractTokenBuilder... builders)
    {
        super(builders);
    }
    
    public static MathSymbolsTokenBuilder normal()
    {
        return new MathSymbolsTokenBuilder
        (
                new CharTokenBuilder(MathTokenType.ASSIGN, '='),
                new CharTokenBuilder(MathTokenType.ADD, '+'),
                new CharTokenBuilder(MathTokenType.SUBTRACT, '-'),
                new CharTokenBuilder(MathTokenType.MULTIPLY, '*'),
                new CharTokenBuilder(MathTokenType.DIVIDE, '/'),
                new CharTokenBuilder(MathTokenType.MODULUS, '%'),
                new MatchingTokenBuilder(MathTokenType.ADD_ASSIGN, "+="),
                new MatchingTokenBuilder(MathTokenType.SUBTRACT_ASSIGN, "-="),
                new MatchingTokenBuilder(MathTokenType.MULTIPLY_ASSIGN, "*="),
                new MatchingTokenBuilder(MathTokenType.DIVIDE_ASSIGN, "/="),
                new MatchingTokenBuilder(MathTokenType.MODULUS_ASSIGN, "%=")
        );
    }
}
