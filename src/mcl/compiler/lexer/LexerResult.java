package mcl.compiler.lexer;

import mcl.compiler.exceptions.MCLError;

import java.util.List;

public record LexerResult(List<Token<?>> tokens, MCLError error)
{
    public LexerResult(List<Token<?>> tokens) { this(tokens, null); }
    public LexerResult(MCLError error) { this(null, error); }
}
