package mcl.grammar;

import compiler.core.parser.IGrammarRule;
import compiler.core.parser.grammar.BlockBracesRule;

public final class MCLRules
{
    public static final ProgramRule         PROGRAM             = new ProgramRule();
    
    public static final NamespaceRule       NAMESPACE           = new NamespaceRule();
    public static final IGrammarRule<?>     NAMESPACE_STATEMENT = null;
    public static final BlockBracesRule     NAMESPACE_BODY      = new BlockBracesRule(NAMESPACE_STATEMENT);
}
