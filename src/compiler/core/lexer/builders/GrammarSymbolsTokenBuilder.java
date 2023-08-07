package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.TokenBuilderList;
import compiler.core.lexer.base.GrammarTokenType;

public class GrammarSymbolsTokenBuilder extends TokenBuilderList
{
    public GrammarSymbolsTokenBuilder(AbstractTokenBuilder... builders)
    {
        super(builders);
    }
    
    public static GrammarSymbolsTokenBuilder normal()
    {
        return new GrammarSymbolsTokenBuilder
        (
                new CharTokenBuilder(GrammarTokenType.LBRACE, '{'),
                new CharTokenBuilder(GrammarTokenType.RBRACE, '}'),
                new CharTokenBuilder(GrammarTokenType.LPAREN, '('),
                new CharTokenBuilder(GrammarTokenType.RPAREN, ')'),
                new CharTokenBuilder(GrammarTokenType.LBRACKET, '['),
                new CharTokenBuilder(GrammarTokenType.RBRACKET, ']'),
                new CharTokenBuilder(GrammarTokenType.SEMICOLON, ';'),
                new CharTokenBuilder(GrammarTokenType.COLON, ':'),
                new CharTokenBuilder(GrammarTokenType.COMMA, ','),
                new CharTokenBuilder(GrammarTokenType.DOT, '.')
        );
    }
}
