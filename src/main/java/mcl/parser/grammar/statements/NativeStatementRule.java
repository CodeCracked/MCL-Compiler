package mcl.parser.grammar.statements;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.nodes.natives.NativeStatementNode;

public class NativeStatementRule implements IGrammarRule<NativeStatementNode>
{
    @Override
    public Result<NativeStatementNode> build(Parser parser)
    {
        Result<NativeStatementNode> result = new Result<>();
        
        // Keyword
        Token keyword = result.register(tokenType(parser, MCLKeyword.NATIVE, "'native'"));
        if (result.getFailure() != null) return result;
        
        // Commands
        Token commands = result.register(tokenType(parser, LiteralTokenType.STRING, "string literal"));
        if (result.getFailure() != null) return result;
        
        // Semicolon
        Token semicolon = result.register(tokenType(parser, GrammarTokenType.SEMICOLON, "';'"));
        if (result.getFailure() != null) return result;
        
        return result.success(new NativeStatementNode(keyword, commands, semicolon));
    }
}
