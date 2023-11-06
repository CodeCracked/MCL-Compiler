package mcl.parser;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.GrammarRuleChooser;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.grammar.components.ArgumentListRule;
import compiler.core.parser.grammar.components.BlockBracesRule;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import mcl.lexer.MCLKeyword;
import mcl.parser.grammar.MCLFileRule;
import mcl.parser.grammar.NamespaceRule;
import mcl.parser.grammar.components.FunctionCallRule;
import mcl.parser.grammar.components.QualifiedIdentifierRule;
import mcl.parser.grammar.components.VariableAccessRule;
import mcl.parser.grammar.declarations.EventDeclarationRule;
import mcl.parser.grammar.declarations.ListenerDeclarationRule;
import mcl.parser.grammar.natives.NativeBindListRule;
import mcl.parser.grammar.natives.NativeBindSpecifierRule;
import mcl.parser.grammar.natives.NativeMethodDeclarationRule;
import mcl.parser.grammar.statements.FunctionCallStatementRule;
import mcl.parser.grammar.statements.NativeStatementRule;
import mcl.parser.grammar.statements.TriggerEventStatementRule;
import mcl.parser.grammar.statements.VariableDeclarationStatementRule;

public final class MCLRules
{
    public static final QualifiedIdentifierRule QUALIFIED_IDENTIFIER = new QualifiedIdentifierRule();
    public static final ExpressionRule EXPRESSION = ExpressionRule.defaultExpression()
            .addOperation(new FunctionCallRule())
            .addOperation(new VariableAccessRule());
    public static final ArgumentListRule ARGUMENT_LIST = new ArgumentListRule(EXPRESSION);
    public static final NativeBindListRule NATIVE_BIND_LIST = new NativeBindListRule();
    public static final NativeBindSpecifierRule NATIVE_BIND_SPECIFIER = new NativeBindSpecifierRule();
    
    public static TriggerEventStatementRule TRIGGER_STATEMENT = new TriggerEventStatementRule();
    public static NativeStatementRule NATIVE_STATEMENT = new NativeStatementRule();
    public static FunctionCallStatementRule FUNCTION_CALL_STATEMENT = new FunctionCallStatementRule();
    public static VariableDeclarationStatementRule VARIABLE_DECLARATION_STATEMENT = new VariableDeclarationStatementRule();
    
    public static IGrammarRule<?> FUNCTION_STATEMENT = new GrammarRuleChooser<>("Not a statement!")
            .addRule(TRIGGER_STATEMENT, MCLKeyword.TRIGGER)
            .addRule(NATIVE_STATEMENT, MCLKeyword.NATIVE)
            .addRule(FUNCTION_CALL_STATEMENT, TokenType.IDENTIFIER, GrammarTokenType.COLON, TokenType.IDENTIFIER, GrammarTokenType.LPAREN)
            .addRule(FUNCTION_CALL_STATEMENT, TokenType.IDENTIFIER, GrammarTokenType.LPAREN)
            .addRule(VARIABLE_DECLARATION_STATEMENT, TokenType.DATA_TYPE, TokenType.IDENTIFIER, GrammarTokenType.SEMICOLON)
            .addRule(VARIABLE_DECLARATION_STATEMENT, TokenType.DATA_TYPE, TokenType.IDENTIFIER, MathTokenType.ASSIGN);
    public static BlockBracesRule FUNCTION_BODY = new BlockBracesRule(FUNCTION_STATEMENT);
    
    public static final EventDeclarationRule EVENT_DECLARATION = new EventDeclarationRule();
    public static final ListenerDeclarationRule LISTENER_DECLARATION = new ListenerDeclarationRule();
    public static NativeMethodDeclarationRule NATIVE_METHOD_DECLARATION = new NativeMethodDeclarationRule();
    
    public static final IGrammarRule<AbstractNode> NAMESPACE_STATEMENT = new GrammarRuleChooser<>("Not a declaration!")
            .addRule(EVENT_DECLARATION, MCLKeyword.EVENT)
            .addRule(LISTENER_DECLARATION, MCLKeyword.LISTENER)
            .addRule(NATIVE_METHOD_DECLARATION, MCLKeyword.NATIVE);
    public static final BlockBracesRule NAMESPACE_BODY = new BlockBracesRule(NAMESPACE_STATEMENT);
    public static final NamespaceRule NAMESPACE = new NamespaceRule();
    
    public static final MCLFileRule SOURCE_FILE = new MCLFileRule();
}
