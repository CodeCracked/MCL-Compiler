package mcl.compiler.parser.nodes;

import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.blocks.BlockDefinitionNode;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;

public class IfStatementNode extends BlockDefinitionNode
{
    public final AbstractNode condition;
    public final AbstractNode trueBlock;
    public final AbstractNode falseBlock;
    public final AbstractNode finallyBlock;

    public Path callFunctionPath;

    public IfStatementNode(Token ifKeyword, AbstractNode condition, AbstractNode trueBlock, AbstractNode falseBlock, AbstractNode finallyNode)
    {
        super(ifKeyword.startPosition(), falseBlock != null ? falseBlock.endPosition() : trueBlock.endPosition(), "if", getBlocks(trueBlock, falseBlock, finallyNode));

        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
        this.finallyBlock = finallyNode;
    }
    private static AbstractNode[] getBlocks(AbstractNode trueBlock, AbstractNode falseBlock, AbstractNode finallyBlock)
    {
        if (finallyBlock != null)
        {
            if (falseBlock != null) return new AbstractNode[] { trueBlock, falseBlock, finallyBlock };
            else return new AbstractNode[] { trueBlock, finallyBlock };
        }
        else
        {
            if (falseBlock != null) return new AbstractNode[] { trueBlock, falseBlock };
            else return new AbstractNode[] { trueBlock };
        }
    }

    @Override
    protected Path getBlockDefinitionFolder(AbstractNode block, Path target)
    {
        if (block.equals(trueBlock)) return target.resolve("true");
        else if (block.equals(falseBlock)) return target.resolve("false");
        else if (block.equals(finallyBlock)) return target.resolve("finally");
        else return target.resolve("other");
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        super.setTranspileTarget(transpiler, target);

        callFunctionPath = transpileTarget.resolve("call.mcfunction");
        transpiler.createFile(callFunctionPath);
        condition.setTranspileTarget(transpiler, callFunctionPath);
    }

    @Override
    protected MCLError transpileStatement(MCLTranspiler transpiler, Path target)
    {
        return transpiler.runFunctionFile(target, callFunctionPath);
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        MCLError error = super.transpile(transpiler);
        if (error != null) return error;

        // Transpile Condition Evaluation
        error = transpiler.comment(callFunctionPath, "IF_CONDITION");
        if (error != null) return error;

        error = condition.transpile(transpiler);
        if (error != null) return error;

        error = transpiler.comment(callFunctionPath, "END IF_CONDITION");
        if (error != null) return error;

        // Transpile True Check
        String trueBlockFunction = transpiler.getFunctionName(((BlockStatementNode)trueBlock).mainFunctionPath);
        error = transpiler.appendToFile(callFunctionPath, file -> file.println(transpiler.applyConfig("execute if score r0 {config.expressions} matches 1.. run function %s", trueBlockFunction)));
        if (error != null) return error;

        // Transpile False Check
        if (falseBlock != null)
        {
            String falseBlockFunction;
            if (falseBlock instanceof IfStatementNode elseIf) falseBlockFunction = transpiler.getFunctionName(elseIf.callFunctionPath);
            else falseBlockFunction = transpiler.getFunctionName(((BlockStatementNode)falseBlock).mainFunctionPath);
            error = transpiler.appendToFile(callFunctionPath, file -> file.println(transpiler.applyConfig("execute if score r0 {config.expressions} matches ..0 run function %s", falseBlockFunction)));
            if (error != null) return error;
        }

        // Transpile Finally
        if (finallyBlock != null)
        {
            Path finallyBlockFunction;
            if (finallyBlock instanceof IfStatementNode finallyIf) finallyBlockFunction = finallyIf.callFunctionPath;
            else finallyBlockFunction = ((BlockStatementNode)finallyBlock).mainFunctionPath;
            return transpiler.runFunctionFile(callFunctionPath, finallyBlockFunction);
        }

        return null;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("IF");

        condition.debugPrint(depth + 1);
        trueBlock.debugPrint(depth + 1);
        if (falseBlock != null) falseBlock.debugPrint(depth + 1);
        if (finallyBlock != null) finallyBlock.debugPrint(depth + 1);
    }
}
