package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLTranspileError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;

public abstract class ExpressionNode extends AbstractNode
{
    public static class TranspileResult
    {
        public final MCLError error;
        public final int returnCode;
        public final int nextAvailableDepthCode;

        public TranspileResult(MCLError error, int returnCode, int nextAvailableDepthCode)
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

    public final MCLError transpile(MCLTranspiler transpiler, Path target, RuntimeType targetType)
    {
        return castAndTranspile(transpiler, target, targetType, 0).error;
    }
    protected final TranspileResult castAndTranspile(MCLTranspiler transpiler, Path target, RuntimeType targetType, int depth)
    {
        ExpressionNode simplified = implicitCast(targetType);
        TranspileResult result = simplified.transpileExpression(transpiler, target, targetType, depth);
        if (result.error != null) return result;

        MCLError error = null;
        if (targetType.equals(RuntimeType.FLOAT) && simplified.getRuntimeType(transpiler.getCompiler()).equals(RuntimeType.INTEGER)) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result storage {config.variables} scaling float %s run scoreboard players get r%s {config.expressions}", targetType.scaleUp(transpiler.getCompiler().config), depth));
            file.println(transpiler.applyConfig("execute store result score r%s {config.expressions} run data get storage {config.variables} scaling 1", depth));
        });

        return new TranspileResult(error, result.returnCode, result.nextAvailableDepthCode);
    }
    protected abstract TranspileResult transpileExpression(MCLTranspiler transpiler, Path target, RuntimeType targetType, int depth);

    @Override
    public final MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        return new MCLTranspileError(transpiler.getSource(), this, "Cannot transpile ExpressionNode with (MCLTranspiler, Path)! Use transpile(MCLTranspiler, Path, RuntimeType) instead");
    }
}
