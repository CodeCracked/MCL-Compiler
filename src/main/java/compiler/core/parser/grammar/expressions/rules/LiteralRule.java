package compiler.core.parser.grammar.expressions.rules;

import compiler.core.lexer.Token;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.LiteralNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;
import compiler.core.util.types.DataType;

public class LiteralRule implements IExpressionGrammarRule
{
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        return parser.getDataTypes().lookupLiteralType(parser.getCurrentToken().type()) != DataType.UNKNOWN;
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
    
        // Token
        Token literal = parser.getCurrentToken();
        DataType dataType = parser.getDataTypes().lookupLiteralType(literal.type());
        if (dataType.equals(DataType.UNKNOWN)) return result.failure(UnexpectedTokenException.explicit(parser, "Not a literal!"));
        parser.advance();
        result.registerAdvancement();
    
        return result.success(new LiteralNode(literal.start(), literal.end(), dataType, literal.contents()));
    }
}
