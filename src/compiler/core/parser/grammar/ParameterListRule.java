package compiler.core.parser.grammar;

import compiler.core.exceptions.UnexpectedTokenException;
import compiler.core.lexer.Token;
import compiler.core.lexer.types.DataType;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.IdentifierNode;
import compiler.core.parser.nodes.ParameterDeclarationNode;
import compiler.core.parser.nodes.ParameterListNode;
import compiler.core.util.Result;

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
        do
        {
            parameters.add(result.register(buildParameter(parser)));
            if (result.getFailure() != null) return result;
        }
        while (parser.getCurrentToken().type() == GrammarTokenType.COMMA);
        
        return result.success(parameters);
    }
    private Result<ParameterDeclarationNode> buildParameter(Parser parser)
    {
        Result<ParameterDeclarationNode> result = new Result<>();
        
        // Data Type
        Token typeKeyword = parser.getCurrentToken();
        DataType dataType = parser.getDataType(typeKeyword);
        if (dataType == null) return result.failure(UnexpectedTokenException.explicit(parser, "Not a data type!"));
        parser.advance();
        result.registerAdvancement();
        
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new ParameterDeclarationNode(typeKeyword, dataType, identifier));
    }
}
