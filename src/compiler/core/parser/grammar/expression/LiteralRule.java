package compiler.core.parser.grammar.expression;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.expression.LiteralNode;
import compiler.core.types.DataType;
import compiler.core.util.Result;

public class LiteralRule implements IGrammarRule<LiteralNode>
{
    @Override
    public Result<LiteralNode> build(Parser parser)
    {
        Result<LiteralNode> result = new Result<>();
        
        // Token
        Token literal = parser.getCurrentToken();
        DataType dataType = parser.getDataTypes().lookupLiteralType(literal.type());
        if (dataType.equals(DataType.UNKNOWN)) return result.failure(UnexpectedTokenException.explicit(parser, "Not a literal!"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new LiteralNode(literal.start(), literal.end(), dataType, literal.contents()));
    }
}
