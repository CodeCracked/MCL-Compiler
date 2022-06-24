package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.function.BiConsumer;

public class NumberNode extends ExpressionNode
{
    public final Token token;

    public NumberNode(Token token)
    {
        super(token.startPosition(), token.endPosition());
        this.token = token;
    }

    @Override
    public ExpressionNode simplify()
    {
        return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) { }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        if (token.value() instanceof Integer) return RuntimeType.INTEGER;
        else if (token.value() instanceof Float) return RuntimeType.FLOAT;
        else return RuntimeType.UNDEFINED;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println(token);
    }

    @Override
    public String toString()
    {
        return token.toString();
    }
}
