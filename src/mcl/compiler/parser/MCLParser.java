package mcl.compiler.parser;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.source.MCLSourceCollection;

import java.util.List;

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

    public void advance()
    {
        this.tokenIndex++;
        this.currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : null;
    }
    public ParseResult parse()
    {
        ParseResult result = GrammarRules.PROGRAM_ROOT.build(this);
        if (result.error() == null && currentToken.type() != TokenType.EOF) return result.failure(new MCLSyntaxError(source.getCodeLocation(currentToken.startPosition()),
                source.getCodeLocation(currentToken.endPosition()), "Expected '+', '-', '*', or '/'"));
        return result;
    }

    public MCLSourceCollection getSource() { return source; }
    public Token getCurrentToken() { return currentToken; }
}
