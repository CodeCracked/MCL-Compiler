package mcl.compiler.parser;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.nodes.expressions.BinaryOpNode;
import mcl.compiler.parser.nodes.expressions.ExpressionNode;
import mcl.compiler.parser.rules.*;
import mcl.compiler.parser.rules.blocks.BlockStatementRule;
import mcl.compiler.parser.rules.blocks.FunctionDefinitionRule;
import mcl.compiler.parser.rules.blocks.NamespaceDefinitionRule;
import mcl.compiler.parser.rules.blocks.ProgramRootRule;
import mcl.compiler.parser.rules.variables.VariableDefinitionRule;
import mcl.compiler.parser.rules.variables.VariableSignatureRule;

import java.util.Set;

public class GrammarRules
{
    public static final GrammarRule PROGRAM_ROOT = new ProgramRootRule();
    public static final GrammarRule NAMESPACE = new NamespaceDefinitionRule();
    public static final GrammarRule FUNCTION = new FunctionDefinitionRule();
    public static final GrammarRule VARIABLE_SIGNATURE = new VariableSignatureRule();
    public static final GrammarRule STATEMENT = new StatementRule();
    public static final GrammarRule VARIABLE_DEFINITION = new VariableDefinitionRule();

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
