package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLTranspileError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;

public abstract class ExpressionNode extends AbstractNode
{
    public static class ExpressionTranspileResult
    {
        public MCLError error;
        public final int returnCode;
        public int nextAvailableDepthCode;

        public ExpressionTranspileResult(MCLError error, int returnCode, int nextAvailableDepthCode)
        {
            this.error = error;
            this.returnCode = returnCode;
            this.nextAvailableDepthCode = nextAvailableDepthCode;
        }
    }

    public ExpressionNode(int startPosition, int endPosition)
    {
        super(startPosition, endPosition);
    }

    public abstract ExpressionNode simplify();
    public ExpressionNode implicitCast(RuntimeType targetType) { return this; }

    public final MCLError transpile(MCLTranspiler transpiler, RuntimeType targetType) throws IOException
    {
        return castAndTranspile(transpiler, targetType, 0).error;
    }
    protected final ExpressionTranspileResult castAndTranspile(MCLTranspiler transpiler, RuntimeType targetType, int depth) throws IOException
    {
        ExpressionNode simplified = implicitCast(targetType);
        ExpressionTranspileResult result = simplified.transpileExpression(transpiler, targetType, depth);
        if (result.error != null) return result;

        MCLError error = null;
        if (targetType.equals(RuntimeType.FLOAT) && simplified.getRuntimeType(transpiler.getCompiler()).equals(RuntimeType.INTEGER)) error = transpiler.appendToFile(transpileTarget, file ->
        {
            file.println(transpiler.applyConfig("execute store result storage {config.variables} scaling float %s run scoreboard players get r%s {config.expressions}", targetType.scaleUp(transpiler.getCompiler().config), depth));
            file.println(transpiler.applyConfig("execute store result score r%s {config.expressions} run data get storage {config.variables} scaling 1", depth));
        });

        return new ExpressionTranspileResult(error, result.returnCode, result.nextAvailableDepthCode);
    }
    protected abstract ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, RuntimeType targetType, int depth) throws IOException;

    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        return new MCLTranspileError(transpiler.getSource(), this, "Cannot transpile ExpressionNode with (MCLTranspiler, Path)! Use transpile(MCLTranspiler, Path, RuntimeType) instead");
    }
}
