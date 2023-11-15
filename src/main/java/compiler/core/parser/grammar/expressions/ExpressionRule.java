package compiler.core.parser.grammar.expressions;

import compiler.core.lexer.types.ComparisonTokenType;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.rules.*;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionRule implements IGrammarRule<AbstractValueNode>, IExpressionGrammarRule
{
    private record Operation(int precedence, IExpressionGrammarRule rule) {}
    
    private final List<Operation> operations = new ArrayList<>();
    private final Map<IExpressionGrammarRule, IExpressionGrammarRule> argumentRuleMap = new HashMap<>();
    private boolean dirty = false;
    private int maxPrecedence = -1;
    
    //region Creation
    private ExpressionRule() {}
    public static ExpressionRule empty() { return new ExpressionRule(); }
    
    public ExpressionRule addOperation(IExpressionGrammarRule rule) { return addOperation(maxPrecedence + 1, rule); }
    public ExpressionRule addOperation(int precedence, IExpressionGrammarRule rule)
    {
        boolean inserted = false;
        for (int i = 0; i < operations.size(); i++)
        {
            if (precedence > operations.get(i).precedence)
            {
                operations.add(i, new Operation(precedence, rule));
                inserted = true;
                break;
            }
        }
        if (!inserted) operations.add(new Operation(precedence, rule));
        if (precedence != Integer.MAX_VALUE) this.maxPrecedence = Math.max(maxPrecedence, precedence);
    
        this.dirty = true;
        return this;
    }
    //endregion
    //region Defaults
    public static ExpressionRule defaultExpression()
    {
        return empty()
                .addOperation(new BinaryOperationRule(MathTokenType.AND, MathTokenType.OR))
                .addOperation(new BinaryOperationRule(ComparisonTokenType.values()))
                .addOperation(new BinaryOperationRule(MathTokenType.ADD, MathTokenType.SUBTRACT))
                .addOperation(new BinaryOperationRule(MathTokenType.MULTIPLY, MathTokenType.DIVIDE, MathTokenType.MODULUS))
                .addOperation(UnaryOperationRule.leading(MathTokenType.SUBTRACT))
                .addOperation(new CastOperationRule())
                .addOperation(new LiteralRule())
                .addOperation(new ParenthesisOperationRule())
                ;
    }
    //endregion
    //region Implementations
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        parser.markPosition();
        boolean canBuild = build(parser).getFailure() == null;
        parser.revertPosition();
        return canBuild;
    }
    
    @Override public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule) { return build(parser); }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser)
    {
        if (dirty) undirty();
        return build(parser, operations.get(operations.size() - 1).rule);
    }
    //endregion
    //region Helpers
    public Result<AbstractValueNode> buildArgument(Parser parser, IExpressionGrammarRule caller)
    {
        IExpressionGrammarRule argumentRule = argumentRuleMap.get(caller);
        if (argumentRule == null) return Result.fail(UnexpectedTokenException.explicit(parser, "Not an expression!"));
        return build(parser, argumentRule);
    }
    
    private void undirty()
    {
        argumentRuleMap.clear();
        IExpressionGrammarRule previousRule = null;
        for (Operation operation : operations)
        {
            if (previousRule != null) argumentRuleMap.put(operation.rule, previousRule);
            previousRule = operation.rule;
        }
        this.dirty = false;
    }
    private Result<AbstractValueNode> build(Parser parser, IExpressionGrammarRule component)
    {
        if (component.canBuild(parser, this)) return component.build(parser, this);
        else return buildArgument(parser, component);
    }
    //endregion
}
