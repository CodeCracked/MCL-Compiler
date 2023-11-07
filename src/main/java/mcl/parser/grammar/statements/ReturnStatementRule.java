package mcl.parser.grammar.statements;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.statements.ReturnStatementNode;

public class ReturnStatementRule implements IGrammarRule<ReturnStatementNode>
{
    @Override
    public Result<ReturnStatementNode> build(Parser parser)
    {
        Result<ReturnStatementNode> result = new Result<>();
        
        // Keyword
        Token keyword = result.register(tokenType(parser, MCLKeyword.RETURN, "'return'"));
        if (result.getFailure() != null) return result;
        
        // Expression
        AbstractValueNode expression = result.register(MCLRules.EXPRESSION.build(parser));
        if (result.getFailure() != null) return result;
        
        // Semicolon
        Token semicolon = result.register(tokenType(parser, GrammarTokenType.SEMICOLON, "';'"));
        if (result.getFailure() != null) return result;
        
        return result.success(new ReturnStatementNode(keyword, expression, semicolon));
    }
}
