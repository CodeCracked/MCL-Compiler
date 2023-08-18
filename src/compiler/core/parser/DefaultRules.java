package compiler.core.parser;

import compiler.core.parser.grammar.IdentifierRule;
import compiler.core.parser.grammar.ParameterListRule;

public class DefaultRules
{
    public static final IdentifierRule IDENTIFIER = new IdentifierRule();
    public static final ParameterListRule PARAMETER_LIST = new ParameterListRule();
}
