package compiler.core.parser;

import compiler.core.parser.grammar.components.DataTypeRule;
import compiler.core.parser.grammar.components.FunctionSignatureRule;
import compiler.core.parser.grammar.components.IdentifierRule;
import compiler.core.parser.grammar.components.ParameterListRule;

public class DefaultRules
{
    public static final DataTypeRule DATA_TYPE = new DataTypeRule();
    public static final IdentifierRule IDENTIFIER = new IdentifierRule();
    public static final ParameterListRule PARAMETER_LIST = new ParameterListRule();
    
    public static final FunctionSignatureRule FUNCTION_SIGNATURE = new FunctionSignatureRule();
}
