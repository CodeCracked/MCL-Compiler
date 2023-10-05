package compiler.core.parser;

import compiler.core.util.Result;

public interface IGrammarRule<T extends AbstractNode> extends IParserRule
{
    Result<T> build(Parser parser);
}
