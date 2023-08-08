package mcl.lexer;

import compiler.core.lexer.Lexer;
import compiler.core.lexer.builders.*;

import java.util.Set;

public class MCLLexer extends Lexer
{
    private static final Set<String> KEYWORDS = Set.of
    (
            "namespace",
            "event",
            "listener",
            "trigger"
    );
    
    public MCLLexer()
    {
        super
        (
                // Ignored Tokens
                WhitespaceTokenBuilder.ignore(), CommentTokenBuilder.ignore(),
                
                // Literal Tokens
                NumberLiteralsTokenBuilder.normal(),
                StringLiteralTokenBuilder.withRaw(),
                
                // Symbol Tokens
                ComparisonSymbolsTokenBuilder.normal(),
                MathSymbolsTokenBuilder.normal(),
                GrammarSymbolsTokenBuilder.normal(),
                
                // Complex Tokens
                IdentifierTokenBuilder.camelCase(KEYWORDS)
        );
    }
}
