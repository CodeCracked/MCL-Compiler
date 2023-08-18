package mcl.grammar;

import compiler.core.parser.GrammarRuleList;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.grammar.BlockBracesRule;
import mcl.grammar.declarations.EventDeclarationRule;

public final class MCLRules
{
    public static final EventDeclarationRule EVENT_DECLARATION_RULE = new EventDeclarationRule();
    
    public static final IGrammarRule<?>     NAMESPACE_STATEMENT = new GrammarRuleList("Not a declaration!", EVENT_DECLARATION_RULE);
    public static final BlockBracesRule     NAMESPACE_BODY      = new BlockBracesRule(NAMESPACE_STATEMENT);
    public static final NamespaceRule       NAMESPACE           = new NamespaceRule();
    
    public static final ProgramRule         PROGRAM             = new ProgramRule();
}
