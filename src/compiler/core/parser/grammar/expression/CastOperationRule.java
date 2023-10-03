package compiler.core.parser.grammar.expression;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.CastOperationNode;
import compiler.core.util.Result;

public class CastOperationRule implements IGrammarRule<CastOperationNode>
{
    @Override
    public Result<CastOperationNode> build(Parser parser)
    {
        Result<CastOperationNode> result = new Result<>();
        
        // Opening Parenthesis
        Token openingParenthesis = result.register(tokenType(parser, GrammarTokenType.LPAREN, "'('"));
        if (result.getFailure() != null) return result;
    
        // Cast Type
        DataTypeNode castType = result.register(DefaultRules.DATA_TYPE.build(parser));
        if (result.getFailure() != null) return result;
    
        // Closing Parenthesis
        Token closingParenthesis = result.register(tokenType(parser, GrammarTokenType.RPAREN, "')'"));
        if (result.getFailure() != null) return result;
    
        // Expression
        AbstractValueNode expression = result.register(ExpressionRule.CURRENT_CONFIGURATION.build(parser));
        if (result.getFailure() != null) return result;
    
        return result.success(new CastOperationNode(openingParenthesis.start(), expression.end(), castType, expression));
    }
}
