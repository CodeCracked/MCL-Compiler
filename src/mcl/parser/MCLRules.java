package mcl.parser;

import compiler.core.parser.GrammarRuleList;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.grammar.BlockBracesRule;
import mcl.parser.grammar.NamespaceRule;
import mcl.parser.grammar.ProgramRule;
import mcl.parser.grammar.components.QualifiedIdentifierRule;
import mcl.parser.grammar.declarations.EventDeclarationRule;
import mcl.parser.grammar.declarations.ListenerDeclarationRule;

public final class MCLRules
{
    public static final QualifiedIdentifierRule QUALIFIED_IDENTIFIER = new QualifiedIdentifierRule();
    
    public static IGrammarRule<?> FUNCTION_STATEMENT = null;
    public static BlockBracesRule FUNCTION_BODY = new BlockBracesRule(FUNCTION_STATEMENT);
    
    public static final EventDeclarationRule EVENT_DECLARATION = new EventDeclarationRule();
    public static final ListenerDeclarationRule LISTENER_DECLARATION = new ListenerDeclarationRule();
    
    public static final IGrammarRule<?> NAMESPACE_STATEMENT = new GrammarRuleList("Not a declaration!", EVENT_DECLARATION, LISTENER_DECLARATION);
    public static final BlockBracesRule NAMESPACE_BODY = new BlockBracesRule(NAMESPACE_STATEMENT);
    public static final NamespaceRule NAMESPACE = new NamespaceRule();
    
    public static final ProgramRule PROGRAM = new ProgramRule();
}
