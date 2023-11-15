package compiler.core.parser.grammar.expressions.rules;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;

public class ParenthesisOperationRule implements IExpressionGrammarRule
{
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        return parser.getCurrentToken().type() == GrammarTokenType.LPAREN;
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
        
        // Opening Parenthesis
        result.register(tokenType(parser, GrammarTokenType.LPAREN, "'('"));
        if (result.getFailure() != null) return result;
        
        // Expression
        AbstractValueNode expression = result.register(expressionRule.build(parser));
        if (result.getFailure() != null) return result;
        
        // Closing Parenthesis
        result.register(tokenType(parser, GrammarTokenType.RPAREN, "')'"));
        if (result.getFailure() != null) return result;
        
        return result.success(expression);
    }
}
