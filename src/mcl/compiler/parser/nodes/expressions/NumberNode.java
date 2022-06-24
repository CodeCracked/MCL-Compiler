package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLTranspileError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
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
    protected TranspileResult transpileExpression(MCLTranspiler transpiler, Path target, int depth)
    {
        MCLError error;

        Integer scoreboardValue = getScoreboardValue(transpiler);
        if (scoreboardValue != null) error = transpiler.appendToFile(target, file -> file.printf("scoreboard players set r%s mcl.expressions %s\n", depth, scoreboardValue));
        else error = new MCLTranspileError(transpiler.getSource(), token, "Invalid number type '" + token.value().getClass().getSimpleName() + "'");

        return new TranspileResult(error, depth, depth + 1);
    }
    private Integer getScoreboardValue(MCLTranspiler transpiler)
    {
        if (token.value() instanceof Integer number) return number;
        else if (token.value() instanceof Float number) return Math.round(number * (int)Math.pow(10, transpiler.getCompiler().config.floatDecimalPlaces));
        else return null;
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
