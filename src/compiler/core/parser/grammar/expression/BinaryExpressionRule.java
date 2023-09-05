package compiler.core.parser.grammar.expression;

import compiler.core.lexer.Token;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.BinaryOperationNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

class BinaryExpressionRule implements IGrammarRule<BinaryOperationNode>
{
    private final Enum<?> operationToken;
    private final IGrammarRule<? extends AbstractValueNode> argumentRule;
    
    public BinaryExpressionRule(Enum<?> operationToken, IGrammarRule<? extends AbstractValueNode> argumentRule)
    {
        this.operationToken = operationToken;
        this.argumentRule = argumentRule;
    }
    
    @Override
    public Result<BinaryOperationNode> build(Parser parser)
    {
        Result<BinaryOperationNode> result = new Result<>();
        
        // Left
        AbstractValueNode left = result.register(argumentRule.build(parser));
        if (result.getFailure() != null) return result;
        
        // Operation
        Token operation = parser.getCurrentToken();
        if (operation.type() != operationToken) return result.failure(UnexpectedTokenException.expected(parser, operationToken.name()));
        parser.advance();
        result.registerAdvancement();
        
        // Right
        AbstractValueNode right = result.register(argumentRule.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new BinaryOperationNode(left, operation, right));
    }
}
