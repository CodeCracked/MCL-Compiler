package mcl.compiler.parser;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.nodes.expressions.BinaryOpNode;
import mcl.compiler.parser.nodes.expressions.ExpressionNode;
import mcl.compiler.parser.rules.LocationRule;
import mcl.compiler.parser.rules.blocks.*;
import mcl.compiler.parser.rules.expressions.*;
import mcl.compiler.parser.rules.statements.*;
import mcl.compiler.parser.rules.variables.ParameterListRule;
import mcl.compiler.parser.rules.variables.VariableAssignmentRule;
import mcl.compiler.parser.rules.variables.VariableDefinitionRule;
import mcl.compiler.parser.rules.variables.VariableSignatureRule;

import java.util.Set;

public class GrammarRules
{
    public static final GrammarRule PROGRAM_ROOT = new ProgramRootRule();
    public static final GrammarRule NAMESPACE = new NamespaceDefinitionRule();
    public static final GrammarRule STATEMENT = new StatementRule();

    public static final GrammarRule LOCATION = new LocationRule();
    public static final GrammarRule VARIABLE_SIGNATURE = new VariableSignatureRule();
    public static final GrammarRule VARIABLE_DEFINITION = new VariableDefinitionRule();
    public static final GrammarRule VARIABLE_ASSIGNMENT = new VariableAssignmentRule();
    public static final GrammarRule PARAMETER_LIST = new ParameterListRule();

    public static final GrammarRule EVENT_DEFINITION = new EventDefinitionRule();
    public static final GrammarRule FUNCTION_DEFINITION = new FunctionDefinitionRule();
    public static final GrammarRule FUNCTION_CALL = new FunctionCallRule();
    public static final GrammarRule LISTENER_DEFINITION = new ListenerDefinitionRule();
    public static final GrammarRule RUN_COMMANDS = new RunCommandsRule();
    public static final GrammarRule RETURN_STATEMENT = new ReturnRule();

    public static final GrammarRule IF_STATEMENT = new IfStatementRule();

    public static final GrammarRule EXPRESSION = new ExpressionRule();
    public static final GrammarRule COMPARISON_EXPRESSION = new ComparisonExpressionRule();
    public static final GrammarRule ARITHMETIC_EXPRESSION = new ArithmeticExpressionRule();
    public static final GrammarRule FACTOR = new FactorRule();
    public static final GrammarRule ATOM = new AtomRule();

    public static GrammarRule blockStatement(int indent)
    {
        return new BlockStatementRule(indent);
    }
    public static ParseResult binaryOperationRule(MCLParser parser, GrammarRule argumentRule, Set<Token> operations)
    {
        ParseResult result = new ParseResult();
        AbstractNode left = result.register(argumentRule.build(parser));
        if (result.error() != null) return result;

        while (parser.getCurrentToken() != null)
        {
            boolean foundOperation = false;
            for (Token operationCheck : operations)
            {
                if (parser.getCurrentToken().matches(operationCheck))
                {
                    Token operation = parser.getCurrentToken();
                    result.registerAdvancement();
                    parser.advance();
                    AbstractNode right = result.register(argumentRule.build(parser));
                    if (result.error() != null) return result;

                    left = new BinaryOpNode(left, operation, right);
                    foundOperation = true;
                    break;
                }
            }
            if (!foundOperation) break;
        }

        if (parser.getCompiler().config.simplifyExpressions && left instanceof ExpressionNode expressionNode) left = expressionNode.simplify();
        return result.success(left);
    }
}
