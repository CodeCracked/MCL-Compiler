package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.exceptions.MCLError;
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

    protected abstract TranspileResult transpileExpression(MCLTranspiler transpiler, Path target, int depth);
    public abstract ExpressionNode simplify();

    @Override
    public final MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        TranspileResult result = transpileExpression(transpiler, target, 0);
        return result.error;
    }
}
