package mcl.parser.grammar.natives;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.TokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.parser.MCLRules;
import mcl.parser.nodes.natives.NativeBindListNode;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.util.ArrayList;
import java.util.List;

public class NativeBindListRule implements IGrammarRule<NativeBindListNode>
{
    @Override
    public Result<NativeBindListNode> build(Parser parser)
    {
        Result<NativeBindListNode> result = new Result<>();
        
        // Opening Brace
        Token openingBrace = result.register(tokenType(parser, GrammarTokenType.LBRACE, "'{'"));
        if (result.getFailure() != null) return result;
    
        // Binds
        List<NativeBindSpecifierNode> parameterBinds = new ArrayList<>();
        NativeBindSpecifierNode returnBind = null;
        
        // Only build binds if the list isn't empty
        if (parser.getCurrentToken().type() == TokenType.IDENTIFIER)
        {
            while (true)
            {
                // Parse Bind
                NativeBindSpecifierNode bind = result.register(MCLRules.NATIVE_BIND_SPECIFIER.build(parser));
                if (result.getFailure() != null) return result;
    
                // Process Bind
                if (bind.parameter.value.equals("return"))
                {
                    if (returnBind == null) returnBind = bind;
                    else return result.failure(new CompilerException(bind.parameter.start(), bind.parameter.end(), "Native method return already has a bind!"));
                }
                else parameterBinds.add(bind);
                
                // Check For Comma
                if (parser.getCurrentToken().type() == GrammarTokenType.COMMA)
                {
                    parser.advance();
                    result.registerAdvancement();
                }
                else break;
            }
        }
        
        // Closing Brace
        Token closingBrace = result.register(tokenType(parser, GrammarTokenType.RBRACE, "'}'"));
        if (result.getFailure() != null) return result;
    
        return result.success(new NativeBindListNode(openingBrace, parameterBinds, returnBind, closingBrace));
    }
}
