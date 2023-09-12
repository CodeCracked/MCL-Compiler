package compiler.core.parser;

import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.parser.grammar.components.ArgumentListRule;
import compiler.core.parser.grammar.components.DataTypeRule;
import compiler.core.parser.grammar.components.IdentifierRule;
import compiler.core.parser.grammar.components.ParameterListRule;
import compiler.core.parser.grammar.expression.LiteralRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;

public class DefaultRules
{
    public static final DataTypeRule DATA_TYPE = new DataTypeRule();
    public static final LiteralRule LITERAL = new LiteralRule();
    public static final GrammarRuleChooser<AbstractValueNode> ATOM = new GrammarRuleChooser<AbstractValueNode>("Not an expression!")
            .addRule(LITERAL, LiteralTokenType.class);
    
    public static final IdentifierRule IDENTIFIER = new IdentifierRule();
    public static final ParameterListRule PARAMETER_LIST = new ParameterListRule();
}
