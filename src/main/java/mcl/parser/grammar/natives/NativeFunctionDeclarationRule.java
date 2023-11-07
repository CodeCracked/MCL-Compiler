package mcl.parser.grammar.natives;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.natives.NativeBindListNode;
import mcl.parser.nodes.natives.NativeFunctionDeclarationNode;

public class NativeFunctionDeclarationRule implements IGrammarRule<NativeFunctionDeclarationNode>
{
    @Override
    public Result<NativeFunctionDeclarationNode> build(Parser parser)
    {
        Result<NativeFunctionDeclarationNode> result = new Result<>();
        
        // Keyword
        Token keyword = result.register(tokenType(parser, MCLKeyword.NATIVE, "'native'"));
        if (result.getFailure() != null) return result;
        
        // Signature
        FunctionSignatureNode signature = result.register(DefaultRules.FUNCTION_SIGNATURE.build(parser));
        if (result.getFailure() != null) return result;
        
        // Colon
        result.register(tokenType(parser, GrammarTokenType.COLON, "':'"));
        if (result.getFailure() != null) return result;
        
        // Native Function
        Token nativeFunction = result.register(tokenType(parser, LiteralTokenType.STRING, "string literal"));
        if (result.getFailure() != null) return result;
        
        // Bind List
        NativeBindListNode binds = result.register(MCLRules.NATIVE_BIND_LIST.build(parser));
        if (result.getFailure() != null) return result;
    
        return result.success(new NativeFunctionDeclarationNode(keyword, signature, nativeFunction, binds));
    }
}
