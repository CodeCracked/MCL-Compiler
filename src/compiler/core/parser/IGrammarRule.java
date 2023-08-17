package compiler.core.parser;

import compiler.core.util.Result;

public interface IGrammarRule<T extends AbstractNode>
{
    Result<T> build(Parser parser);
}
