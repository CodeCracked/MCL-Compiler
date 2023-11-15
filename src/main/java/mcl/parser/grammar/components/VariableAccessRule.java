package mcl.parser.grammar.components;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.Parser;
import compiler.core.parser.grammar.expressions.ExpressionRule;
import compiler.core.parser.grammar.expressions.IExpressionGrammarRule;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.nodes.components.VariableAccessNode;

public class VariableAccessRule implements IExpressionGrammarRule
{
    @Override
    public boolean canBuild(Parser parser, ExpressionRule expressionRule)
    {
        return parser.getCurrentToken().type() == TokenType.IDENTIFIER && parser.peekNextToken().type() != GrammarTokenType.LPAREN;
    }
    
    @Override
    public Result<AbstractValueNode> build(Parser parser, ExpressionRule expressionRule)
    {
        Result<AbstractValueNode> result = new Result<>();
        
        // Identifier
        QualifiedIdentifierNode identifier = result.register(MCLRules.QUALIFIED_IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        return result.success(new VariableAccessNode(identifier));
    }
}
