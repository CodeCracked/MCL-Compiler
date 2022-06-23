package mcl.compiler.parser;

public interface GrammarRule
{
    ParseResult build(MCLParser parser);
}
