package mcl.parser;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.GrammarRuleChooser;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.grammar.components.ArgumentListRule;
import compiler.core.parser.grammar.components.BlockBracesRule;
import compiler.core.parser.grammar.expression.ExpressionRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import mcl.lexer.MCLKeyword;
import mcl.parser.grammar.MCLFileRule;
import mcl.parser.grammar.NamespaceRule;
import mcl.parser.grammar.components.FunctionCallRule;
import mcl.parser.grammar.components.QualifiedIdentifierRule;
import mcl.parser.grammar.declarations.EventDeclarationRule;
import mcl.parser.grammar.declarations.ListenerDeclarationRule;
import mcl.parser.grammar.statements.FunctionCallStatementRule;
import mcl.parser.grammar.statements.NativeStatementRule;
import mcl.parser.grammar.statements.TriggerEventStatementRule;

public final class MCLRules
{
    public static final QualifiedIdentifierRule QUALIFIED_IDENTIFIER = new QualifiedIdentifierRule();
    public static final IGrammarRule<AbstractValueNode> EXPRESSION = ExpressionRule.defaultRule();
    public static final ArgumentListRule ARGUMENT_LIST = new ArgumentListRule(EXPRESSION);
    public static final FunctionCallRule FUNCTION_CALL = new FunctionCallRule();
    
    public static TriggerEventStatementRule TRIGGER_STATEMENT = new TriggerEventStatementRule();
    public static FunctionCallStatementRule FUNCTION_CALL_STATEMENT = new FunctionCallStatementRule();
    public static NativeStatementRule NATIVE_STATEMENT = new NativeStatementRule();
    
    public static IGrammarRule<?> FUNCTION_STATEMENT = new GrammarRuleChooser<>("Not a statement!")
            .addRule(EXPRESSION, LiteralTokenType.class)
            .addRule(TRIGGER_STATEMENT, MCLKeyword.TRIGGER)
            .addRule(FUNCTION_CALL_STATEMENT, TokenType.IDENTIFIER, GrammarTokenType.COLON, TokenType.IDENTIFIER, GrammarTokenType.LPAREN)
            .addRule(FUNCTION_CALL_STATEMENT, TokenType.IDENTIFIER, GrammarTokenType.LPAREN)
            .addRule(NATIVE_STATEMENT, MCLKeyword.NATIVE);
    public static BlockBracesRule FUNCTION_BODY = new BlockBracesRule(FUNCTION_STATEMENT);
    
    public static final EventDeclarationRule EVENT_DECLARATION = new EventDeclarationRule();
    public static final ListenerDeclarationRule LISTENER_DECLARATION = new ListenerDeclarationRule();
    
    public static final IGrammarRule<AbstractNode> NAMESPACE_STATEMENT = new GrammarRuleChooser<>("Not a declaration!")
            .addRule(EVENT_DECLARATION, MCLKeyword.EVENT)
            .addRule(LISTENER_DECLARATION, MCLKeyword.LISTENER);
    public static final BlockBracesRule NAMESPACE_BODY = new BlockBracesRule(NAMESPACE_STATEMENT);
    public static final NamespaceRule NAMESPACE = new NamespaceRule();
    
    public static final MCLFileRule SOURCE_FILE = new MCLFileRule();
    
    static
    {
        DefaultRules.ATOM
                .addRule(FUNCTION_CALL, TokenType.IDENTIFIER, GrammarTokenType.COLON, TokenType.IDENTIFIER, GrammarTokenType.LPAREN)
                .addRule(FUNCTION_CALL, TokenType.IDENTIFIER, GrammarTokenType.LPAREN);
    }
}
