package compiler.core.parser.grammar.components;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.util.Result;

public class IdentifierRule implements IGrammarRule<IdentifierNode>
{
    @Override
    public Result<IdentifierNode> build(Parser parser)
    {
        Result<IdentifierNode> result = new Result<>();
        
        if (parser.getCurrentToken().type() == TokenType.IDENTIFIER)
        {
            Token identifier = parser.getCurrentToken();
            parser.advance();
            result.registerAdvancement();
            return result.success(new IdentifierNode(identifier));
        }
        else return result.failure(UnexpectedTokenException.expected(parser, "identifier"));
    }
}
