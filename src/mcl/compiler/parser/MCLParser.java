package mcl.compiler.parser;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.source.MCLSourceCollection;

import java.util.List;

public class MCLParser
{
    private final MCLCompiler compiler;
    private final MCLSourceCollection source;
    private final List<Token> tokens;
    private int tokenIndex;
    private Token currentToken;
    private Token nextToken;
    private int currentIndent;

    public MCLParser(MCLCompiler compiler, MCLSourceCollection source, List<Token> tokens)
    {
        this.compiler = compiler;
        this.source = source;
        this.tokens = tokens;
        this.tokenIndex = -1;
        this.currentIndent = 0;
        advance();
    }

    public void advance()
    {
        this.tokenIndex++;
        this.currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : null;
        this.nextToken = (tokenIndex + 1) < tokens.size() ? tokens.get(tokenIndex + 1) : null;

        if (currentToken != null && currentToken.type() == TokenType.INDENT) this.currentIndent = (int)currentToken.value();
    }
    public void setIndex(int index)
    {
        this.tokenIndex = index - 1;
        advance();
    }
    public ParseResult parse()
    {
        ParseResult result = GrammarRules.PROGRAM_ROOT.build(this);
        if (result.error() == null && currentToken.type() != TokenType.EOF) return result.failure(new MCLSyntaxError(source.getCodeLocation(currentToken.startPosition()),
                source.getCodeLocation(currentToken.endPosition()), "Expected 'namespace'"));
        return result;
    }

    public MCLCompiler getCompiler() { return compiler; }
    public MCLSourceCollection getSource() { return source; }
    public Token getCurrentToken() { return currentToken; }
    public Token peekNextToken() { return nextToken; }
    public int getTokenIndex() { return tokenIndex; }
    public int getCurrentIndent() { return currentIndent; }
}
