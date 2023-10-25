package compiler.core.lexer;

import compiler.core.source.SourcePosition;

public abstract class AbstractTokenBuilder
{
    public abstract Token tryBuild(Lexer lexer, SourcePosition position);
}
