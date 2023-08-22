package compiler.core.parser.grammar.components;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.util.Result;

public class DataTypeRule implements IGrammarRule<DataTypeNode>
{
    @Override
    public Result<DataTypeNode> build(Parser parser)
    {
        Result<DataTypeNode> result = new Result<>();
        
        // Data Type
        Token dataType = parser.getCurrentToken();
        if (dataType.type() != TokenType.DATA_TYPE) return result.failure(UnexpectedTokenException.explicit(parser, "Not a data type!"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new DataTypeNode(dataType));
    }
}
