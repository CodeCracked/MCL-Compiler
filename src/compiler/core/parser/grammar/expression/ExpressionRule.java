package compiler.core.parser.grammar.expression;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.ComparisonTokenType;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.GrammarRuleChooser;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.BinaryOperationNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.HashSet;
import java.util.Set;

public final class ExpressionRule
{
    //region Defaults
    public static IGrammarRule<AbstractValueNode> defaultRule()
    {
        return buildExpressionRule(defaultPrecedence(), DefaultRules.ATOM);
    }
    public static Precedence defaultPrecedence()
    {
        return compose(comparison(), booleanAlgebra(), arithmetic(true));
    }
    public static IGrammarRule<AbstractValueNode> buildExpressionRule(Precedence orderOfPrecedence, GrammarRuleChooser<AbstractValueNode> atomRule)
    {
        IGrammarRule<AbstractValueNode> expressionRule = orderOfPrecedence.build(atomRule);
        atomRule.addRule(parser ->
        {
            Result<AbstractValueNode> result = new Result<>();

            // Opening Parenthesis
            Token openingParenthesis = parser.getCurrentToken();
            if (openingParenthesis.type() != GrammarTokenType.LPAREN) return result.failure(UnexpectedTokenException.expected(parser, "'('"));
            parser.advance();
            result.registerAdvancement();

            // Expression
            AbstractValueNode expressionNode = result.register(expressionRule.build(parser));
            if (result.getFailure() != null) return result;

            // Closing Parenthesis
            Token closingParenthesis = parser.getCurrentToken();
            if (closingParenthesis.type() != GrammarTokenType.RPAREN) return result.failure(UnexpectedTokenException.expected(parser, "')'"));
            parser.advance();
            result.registerAdvancement();
            
            return result.success(expressionNode);
        }, GrammarTokenType.LPAREN);
        return expressionRule;
    }
    //endregion
    //region Primitives
    public static Precedence precedenceLevel(OperationType type, Enum<?>... operations)
    {
        return new Precedence(type, operations);
    }
    public static Precedence compose(Precedence... orderOfOperations)
    {
        for (int i = 0; i < orderOfOperations.length - 1; i++) orderOfOperations[i].setComponentPrecedence(orderOfOperations[i + 1]);
        return orderOfOperations[0];
    }
    
    public static Precedence arithmetic(boolean includeModulus)
    {
        return compose(
                precedenceLevel(OperationType.BINARY, MathTokenType.ADD, MathTokenType.SUBTRACT),
                precedenceLevel(OperationType.BINARY, MathTokenType.MULTIPLY, MathTokenType.DIVIDE, includeModulus ? MathTokenType.MODULUS : null)
        );
    }
    public static Precedence booleanAlgebra()
    {
        return precedenceLevel(OperationType.BINARY, MathTokenType.OR, MathTokenType.AND);
    }
    public static Precedence comparison()
    {
        return precedenceLevel(OperationType.BINARY, ComparisonTokenType.values());
    }
    //endregion
    
    //region Builder Types
    private enum OperationType { UNARY, BINARY }
    private static class Precedence
    {
        private final OperationType operationType;
        private final Set<Enum<?>> operations;
        private Precedence componentPrecedence;
        
        Precedence (OperationType operationType, Enum<?>... operations)
        {
            this.operationType = operationType;
            this.operations = new HashSet<>();
            for (Enum<?> operation : operations) if (operation != null) this.operations.add(operation);
        }
        void setComponentPrecedence(Precedence componentPrecedence)
        {
            this.componentPrecedence = componentPrecedence;
        }
        IGrammarRule<AbstractValueNode> build(IGrammarRule<AbstractValueNode> atom)
        {
            IGrammarRule<AbstractValueNode> argumentRule = componentPrecedence != null ? componentPrecedence.build(atom) : atom;
            
            if (operationType == OperationType.BINARY) return binaryOperation(argumentRule, operations);
            
            throw new UnsupportedOperationException("ExpressionRule OperationType " + operationType + " is not currently supported!");
        }
        
        //region Rule Primitives
        private static IGrammarRule<AbstractValueNode> binaryOperation(IGrammarRule<AbstractValueNode> argumentRule, Set<Enum<?>> operations)
        {
            return parser ->
            {
                Result<AbstractValueNode> result = new Result<>();
        
                // Initial Argument
                AbstractValueNode left = result.register(argumentRule.build(parser));
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
                            AbstractValueNode right = result.register(argumentRule.build(parser));
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
            };
        }
        //endregion
    }
    //endregion
}