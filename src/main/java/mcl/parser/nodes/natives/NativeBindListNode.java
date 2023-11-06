package mcl.parser.nodes.natives;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.util.annotations.OptionalChild;

import java.util.List;

public class NativeBindListNode extends AbstractNode
{
    public final List<NativeBindSpecifierNode> parameterBinds;
    @OptionalChild public final NativeBindSpecifierNode returnBind;
    
    public NativeBindListNode(Token openingBrace, List<NativeBindSpecifierNode> parameterBinds, NativeBindSpecifierNode returnBind, Token endingBrace)
    {
        super(openingBrace.start(), endingBrace.end(), false);
        this.parameterBinds = parameterBinds;
        this.returnBind = returnBind;
    }
}
