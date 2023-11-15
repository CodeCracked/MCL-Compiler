package compiler.core.parser.grammar.expressions.rules;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;

public class CastOperationRule implements IExpressionGrammarRule
{
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        return expectTokenTypes(parser, GrammarTokenType.LPAREN, TokenType.DATA_TYPE, GrammarTokenType.RPAREN);
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
    
        // Opening Parenthesis
        Token openingParenthesis = result.register(tokenType(parser, GrammarTokenType.LPAREN, "'('"));
        if (result.getFailure() != null) return result;
    
        // Cast Type
        DataTypeNode castType = result.register(DefaultRules.DATA_TYPE.build(parser));
        if (result.getFailure() != null) return result;
    
        // Closing Parenthesis
        result.register(tokenType(parser, GrammarTokenType.RPAREN, "')'"));
        if (result.getFailure() != null) return result;
        
        // Expression
        AbstractValueNode expression = result.register(expressionRule.buildArgument(parser, this));
        if (result.getFailure() != null) return result;
        
        // Cast
        AbstractValueNode casted = result.register(expression.cast(castType));
        if (result.getFailure() != null) return result;
        
        return result.success(casted);
    }
}
