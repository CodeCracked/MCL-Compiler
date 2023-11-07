package compiler.core.parser.grammar.components;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;

import java.util.ArrayList;
import java.util.List;

public class ParameterListRule implements IGrammarRule<ParameterListNode>
{
    @Override
    public Result<ParameterListNode> build(Parser parser)
    {
        Result<ParameterListNode> result = new Result<>();
        
        // Opening Parenthesis
        Token openingParenthesis = parser.getCurrentToken();
        if (openingParenthesis.type() != GrammarTokenType.LPAREN) return result.failure(UnexpectedTokenException.expected(parser, "'('"));
        parser.advance();
        result.registerAdvancement();
    
        // Parameters
        List<ParameterDeclarationNode> parameters = result.register(buildParameters(parser));
        if (result.getFailure() != null) return result;
        
        // Closing Parenthesis
        Token closingParenthesis = parser.getCurrentToken();
        if (closingParenthesis.type() != GrammarTokenType.RPAREN) return result.failure(UnexpectedTokenException.expected(parser, "')'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new ParameterListNode(openingParenthesis, parameters, closingParenthesis));
    }
    
    private Result<List<ParameterDeclarationNode>> buildParameters(Parser parser)
    {
        Result<List<ParameterDeclarationNode>> result = new Result<>();
        
        // Empty Parameter List
        if (parser.getCurrentToken().type() == GrammarTokenType.RPAREN) return result.success(new ArrayList<>());
        
        // Build Parameters
        List<ParameterDeclarationNode> parameters = new ArrayList<>();
        while (true)
        {
            // Build Parameter
            parameters.add(result.register(buildParameter(parser)));
            if (result.getFailure() != null) return result;
            
            // Check for Comma
            if (parser.getCurrentToken().type() == GrammarTokenType.COMMA)
            {
                parser.advance();
                result.registerAdvancement();
            }
            else break;
        }
        
        return result.success(parameters);
    }
    private Result<ParameterDeclarationNode> buildParameter(Parser parser)
    {
        Result<ParameterDeclarationNode> result = new Result<>();
        
        // Data Type
        DataTypeNode dataType = result.register(DefaultRules.DATA_TYPE.build(parser));
        if (result.getFailure() != null) return result;
        
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new ParameterDeclarationNode(dataType, identifier));
    }
}
