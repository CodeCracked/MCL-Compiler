package mcl.parser.grammar.components;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.parser.nodes.components.QualifiedIdentifierNode;

public class FunctionCallRule implements IGrammarRule<FunctionCallNode>, IExpressionGrammarRule
{
    @Override
    public boolean canBuild(Parser parser)
    {
        return expectTokenTypes(parser, TokenType.IDENTIFIER, GrammarTokenType.COLON, TokenType.IDENTIFIER, GrammarTokenType.LPAREN) ||
                expectTokenTypes(parser, TokenType.IDENTIFIER, GrammarTokenType.LPAREN);
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
        FunctionCallNode functionCall = result.register(build(parser));
        
        if (result.getFailure() != null) return result;
        else return result.success(functionCall);
    }
    
    @Override
    public Result<FunctionCallNode> build(Parser parser)
    {
        Result<FunctionCallNode> result = new Result<>();
        
        // Function
        QualifiedIdentifierNode function = result.register(MCLRules.QUALIFIED_IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Arguments
        ArgumentListNode arguments = result.register(MCLRules.ARGUMENT_LIST.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new FunctionCallNode(function, arguments));
    }
}
