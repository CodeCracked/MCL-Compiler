package mcl.lexer;

import compiler.core.lexer.Lexer;
import compiler.core.lexer.builders.*;

public class MCLLexer extends Lexer
{
    public MCLLexer()
    {
        super
        (
                WhitespaceTokenBuilder.ignore(),
                CommentTokenBuilder.ignore(),
                NumberLiteralsTokenBuilder.normal(),
                StringLiteralTokenBuilder.withRaw(),
                GrammarSymbolsTokenBuilder.normal(),
                IdentifierTokenBuilder.camelCase(KeywordLists.KEYWORDS)
        );
    }
}
