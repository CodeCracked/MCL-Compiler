package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.TokenBuilderList;
import compiler.core.lexer.types.ComparisonTokenType;

public class ComparisonSymbolsTokenBuilder extends TokenBuilderList
{
    public ComparisonSymbolsTokenBuilder(AbstractTokenBuilder... builders)
    {
        super(builders);
    }
    
    public static ComparisonSymbolsTokenBuilder normal()
    {
        return new ComparisonSymbolsTokenBuilder
        (
                new MatchingTokenBuilder(ComparisonTokenType.EQUAL, "=="),
                new MatchingTokenBuilder(ComparisonTokenType.NOT_EQUAL, "!="),
                new CharTokenBuilder(ComparisonTokenType.LESS_THAN, '<'),
                new CharTokenBuilder(ComparisonTokenType.GREATER_THAN, '>'),
                new MatchingTokenBuilder(ComparisonTokenType.LESS_OR_EQUAL, "<="),
                new MatchingTokenBuilder(ComparisonTokenType.GREATER_OR_EQUAL, ">=")
        );
    }
}
