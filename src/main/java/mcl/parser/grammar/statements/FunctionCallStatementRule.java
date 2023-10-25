package mcl.parser.grammar.statements;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.grammar.components.FunctionCallRule;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.parser.nodes.statements.FunctionCallStatementNode;

public class FunctionCallStatementRule implements IGrammarRule<FunctionCallStatementNode>
{
    private final FunctionCallRule callRule = new FunctionCallRule();
    
    @Override
    public Result<FunctionCallStatementNode> build(Parser parser)
    {
        Result<FunctionCallStatementNode> result = new Result<>();
        
        // Function Call
        FunctionCallNode call = result.register(callRule.build(parser));
        if (result.getFailure() != null) return result;
        
        // Semicolon
        Token semicolon = result.register(tokenType(parser, GrammarTokenType.SEMICOLON, "';'"));
        if (result.getFailure() != null) return result;
        
        return result.success(new FunctionCallStatementNode(call, semicolon));
    }
}
