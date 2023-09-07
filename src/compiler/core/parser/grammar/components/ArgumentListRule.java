package compiler.core.parser.grammar.components;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expression.ExpressionRule;
import compiler.core.parser.nodes.components.*;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.ArrayList;
import java.util.List;

public class ArgumentListRule implements IGrammarRule<ArgumentListNode>
{
    private final IGrammarRule<AbstractValueNode> expressionRule;
    
    public ArgumentListRule(IGrammarRule<AbstractValueNode> expressionRule)
    {
        this.expressionRule = expressionRule;
    }
    
    @Override
    public Result<ArgumentListNode> build(Parser parser)
    {
        Result<ArgumentListNode> result = new Result<>();
        
        // Opening Parenthesis
        Token openingParenthesis = parser.getCurrentToken();
        if (openingParenthesis.type() != GrammarTokenType.LPAREN) return result.failure(UnexpectedTokenException.expected(parser, "'('"));
        parser.advance();
        result.registerAdvancement();
    
        // Parameters
        List<AbstractValueNode> arguments = result.register(buildArguments(parser));
        if (result.getFailure() != null) return result;
        
        // Closing Parenthesis
        Token closingParenthesis = parser.getCurrentToken();
        if (closingParenthesis.type() != GrammarTokenType.RPAREN) return result.failure(UnexpectedTokenException.expected(parser, "')'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new ArgumentListNode(openingParenthesis, arguments, closingParenthesis));
    }
    
    private Result<List<AbstractValueNode>> buildArguments(Parser parser)
    {
        Result<List<AbstractValueNode>> result = new Result<>();
        
        // Empty Argument List
        if (parser.getCurrentToken().type() == GrammarTokenType.RPAREN) return result.success(new ArrayList<>());
        
        // Build Arguments
        List<AbstractValueNode> arguments = new ArrayList<>();
        do
        {
            arguments.add(result.register(expressionRule.build(parser)));
            if (result.getFailure() != null) return result;
        }
        while (parser.getCurrentToken().type() == GrammarTokenType.COMMA);
        
        return result.success(arguments);
    }
}
