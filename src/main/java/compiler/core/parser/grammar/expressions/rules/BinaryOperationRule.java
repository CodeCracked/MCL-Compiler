package compiler.core.parser.grammar.expressions.rules;

import compiler.core.lexer.Token;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.BinaryOperationNode;
import compiler.core.util.Result;

public class BinaryOperationRule implements IExpressionGrammarRule
{
    private final Enum<?>[] operations;
    
    public BinaryOperationRule(Enum<?>... operations)
    {
        this.operations = operations;
    }
    
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        return true;
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
    
        // Initial Argument
        AbstractValueNode left = result.register(expressionRule.buildArgument(parser, this));
        if (result.getFailure() != null) return result;
    
        // Optional Operations
        while (parser.getCurrentToken() != null)
        {
            boolean foundOperation = false;
            for (Enum<?> operation : operations)
            {
                // If the next token is the current operation type
                if (parser.getCurrentToken().type() == operation)
                {
                    // Consume the operation token
                    Token operationToken = parser.getCurrentToken();
                    parser.advance();
                    result.registerAdvancement();
                
                    // Right Argument
                    AbstractValueNode right = result.register(expressionRule.buildArgument(parser, this));
                    if (result.getFailure() != null) return result;
                
                    // Update Argument
                    left = new BinaryOperationNode(left, operationToken, right);
                    foundOperation = true;
                    break;
                }
            }
        
            if (!foundOperation) break;
        }
    
        return result.success(left);
    }
}
