package compiler.core.parser.grammar.expressions.rules;

import compiler.core.lexer.Token;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.UnaryOperationNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.Set;

public class UnaryOperationRule implements IExpressionGrammarRule
{
    private final Set<Enum<?>> operations;
    private final boolean leadingOperation;
    
    private UnaryOperationRule(Set<Enum<?>> operations, boolean leadingOperation)
    {
        this.operations = operations;
        this.leadingOperation = leadingOperation;
    }
    public static UnaryOperationRule leading(Enum<?>... operations) { return new UnaryOperationRule(Set.of(operations), true); }
    public static UnaryOperationRule trailing(Enum<?>... operations) { return new UnaryOperationRule(Set.of(operations), false); }
    
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        if (leadingOperation) return operations.contains(parser.getCurrentToken().type());
        else
        {
            parser.markPosition();
            Result<AbstractValueNode> expression = expressionRule.buildArgument(parser, this);
            boolean valid = operations.contains(parser.getCurrentToken().type());
            parser.revertPosition();
            return valid;
        }
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
        SourcePosition start = null;
        SourcePosition end;
        Token operation = null;
        
        // Leading Operation
        if (leadingOperation)
        {
            operation = result.register(token(parser, test -> operations.contains(test.type()), () -> UnexpectedTokenException.expected(parser, "unary operation")));
            start = operation.start();
            if (result.getFailure() != null) return result;
        }
        
        // Expression
        AbstractValueNode expression = result.register(expressionRule.buildArgument(parser, this));
        if (result.getFailure() != null) return result;
        if (start == null) start = expression.start();
    
        // Trailing Operation
        if (!leadingOperation)
        {
            operation = result.register(token(parser, test -> operations.contains(test.type()), () -> UnexpectedTokenException.expected(parser, "unary operation")));
            end = operation.end();
            if (result.getFailure() != null) return result;
        }
        else end = expression.end();
        
        return result.success(new UnaryOperationNode(start, end, operation, expression));
    }
}
