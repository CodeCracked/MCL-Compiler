package compiler.core.parser.grammar;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.BlockNode;
import compiler.core.util.Result;

import java.util.ArrayList;
import java.util.List;

public class BlockBracesRule implements IGrammarRule<BlockNode>
{
    private final IGrammarRule<?> bodyRule;
    
    public BlockBracesRule(IGrammarRule<?> bodyRule)
    {
        this.bodyRule = bodyRule;
    }
    
    @Override
    public Result<BlockNode> build(Parser parser)
    {
        Result<BlockNode> result = new Result<>();
    
        // Opening Brace
        Token openingBrace = parser.getCurrentToken();
        if (openingBrace.type() != GrammarTokenType.LBRACE) return result.failure(UnexpectedTokenException.expected(parser, "'{'"));
        parser.advance();
        result.registerAdvancement();
        
        // Contents
        List<AbstractNode> contents = new ArrayList<>();
        
        if (bodyRule != null)
        {
            while (parser.getCurrentToken() != null && parser.getCurrentToken().type() != GrammarTokenType.RBRACE)
            {
                contents.add(result.register(bodyRule.build(parser)));
                if (result.getFailure() != null) return result;
            }
        }
        else
        {
            int levels = 0;
            while (parser.getCurrentToken() != null && (parser.getCurrentToken().type() != GrammarTokenType.RBRACE || levels > 0))
            {
                if (parser.getCurrentToken().type() == GrammarTokenType.LBRACE) levels++;
                else if (parser.getCurrentToken().type() == GrammarTokenType.RBRACE) levels--;
                parser.advance();
                result.registerAdvancement();
            }
        }
        
        // Closing Brace
        Token closingBrace = parser.getCurrentToken();
        if (closingBrace.type() != GrammarTokenType.RBRACE) return result.failure(UnexpectedTokenException.expected(parser, "'{'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new BlockNode(openingBrace.start(), closingBrace.end(), contents));
    }
}
