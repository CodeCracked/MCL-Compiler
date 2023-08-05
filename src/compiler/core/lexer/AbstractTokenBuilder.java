package compiler.core.lexer;

public abstract class AbstractTokenBuilder<T>
{
    public abstract Token<T> tryBuild(Lexer<T> lexer);
}
