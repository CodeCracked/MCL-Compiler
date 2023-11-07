package mcl.parser.grammar.natives;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.nodes.methods.MethodSignatureNode;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.natives.NativeBindListNode;
import mcl.parser.nodes.natives.NativeMethodDeclarationNode;

public class NativeMethodDeclarationRule implements IGrammarRule<NativeMethodDeclarationNode>
{
    @Override
    public Result<NativeMethodDeclarationNode> build(Parser parser)
    {
        Result<NativeMethodDeclarationNode> result = new Result<>();
        
        // Keyword
        Token keyword = result.register(tokenType(parser, MCLKeyword.NATIVE, "'native'"));
        if (result.getFailure() != null) return result;
        
        // Signature
        MethodSignatureNode signature = result.register(DefaultRules.METHOD_SIGNATURE.build(parser));
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
    
        return result.success(new NativeMethodDeclarationNode(keyword, signature, nativeFunction, binds));
    }
}
