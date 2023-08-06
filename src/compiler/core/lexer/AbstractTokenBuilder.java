package compiler.core.lexer;

import compiler.core.source.SourcePosition;

public abstract class AbstractTokenBuilder<T>
{
    public abstract Token<T> tryBuild(Lexer<T> lexer, SourcePosition position);
}
