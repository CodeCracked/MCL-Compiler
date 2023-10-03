package compiler.core.parser;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.grammar.components.DataTypeRule;
import compiler.core.parser.grammar.components.IdentifierRule;
import compiler.core.parser.grammar.components.ParameterListRule;
import compiler.core.parser.grammar.expression.CastOperationRule;
import compiler.core.parser.grammar.expression.LiteralRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;

public class DefaultRules
{
    public static final DataTypeRule DATA_TYPE = new DataTypeRule();
    public static final LiteralRule LITERAL = new LiteralRule();
    public static final CastOperationRule CAST_OPERATION = new CastOperationRule();
    public static final GrammarRuleChooser<AbstractValueNode> ATOM = new GrammarRuleChooser<AbstractValueNode>("Not an expression!")
            .addRule(LITERAL, LiteralTokenType.class)
            .addRule(CAST_OPERATION, GrammarTokenType.LPAREN, TokenType.DATA_TYPE, GrammarTokenType.RPAREN);
    
    public static final IdentifierRule IDENTIFIER = new IdentifierRule();
    public static final ParameterListRule PARAMETER_LIST = new ParameterListRule();
}
