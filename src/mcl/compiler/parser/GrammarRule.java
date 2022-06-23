package mcl.compiler.parser;

public interface GrammarRule
{
    AbstractNode build(MCLParser parser);
}
