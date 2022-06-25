package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLTranspileError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
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
    public ExpressionNode implicitCast(RuntimeType targetType)
    {
        if (targetType.equals(RuntimeType.FLOAT) && token.value() instanceof Integer number) return new NumberNode(new Token(TokenType.FLOAT, (float)number, token.startPosition(), token.endPosition()));
        else return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) { }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target)
    {
        this.transpileTarget = target;
    }
    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, RuntimeType targetType, int depth)
    {
        MCLError error;

        Integer scoreboardValue = getScoreboardValue(transpiler);
        if (scoreboardValue != null) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players set r%1$s {config.expressions} %2$s", depth, scoreboardValue)));
        else error = new MCLTranspileError(transpiler.getSource(), token, "Invalid number type '" + token.value().getClass().getSimpleName() + "'");

        return new ExpressionTranspileResult(error, depth, depth + 1);
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
