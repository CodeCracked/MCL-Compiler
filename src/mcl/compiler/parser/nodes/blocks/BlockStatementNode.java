package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BlockStatementNode extends AbstractNode
{
    public final List<AbstractNode> statements;
    public Path mainFunctionPath;

    public BlockStatementNode(List<AbstractNode> statements, int start)
    {
        super(start, statements.size() > 0 ? statements.get(statements.size() - 1).endPosition() : start + 1);
        this.statements = Collections.unmodifiableList(statements);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (AbstractNode statement : statements)
        {
            parentChildConsumer.accept(this, statement);
            statement.walk(parentChildConsumer);
        }
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (AbstractNode statement : statements)
        {
            MCLError error = statement.createSymbols(compiler, source);
            if (error != null) return error;
        }
        return null;
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (AbstractNode statement : statements)
        {
            MCLError error = statement.symbolAnalysis(compiler, source);
            if (error != null) return error;
        }
        return null;
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        this.mainFunctionPath = target.resolve("main.mcfunction");
        transpiler.createFile(mainFunctionPath);

        Map<String, Integer> blockTypeCounts = new HashMap<>();
        for (AbstractNode statement : statements)
        {
            Path statementTarget = mainFunctionPath;

            if (statement instanceof NamedBlockDefinitionNode block) statementTarget = transpileTarget.resolve(block.blockName);
            else if (statement instanceof BlockDefinitionNode block)
            {
                if (!blockTypeCounts.containsKey(block.blockName)) blockTypeCounts.put(block.blockName, 1);
                statementTarget = target.resolve(block.blockName + "_" + blockTypeCounts.get(block.blockName));
                blockTypeCounts.put(block.blockName, blockTypeCounts.get(block.blockName) + 1);
            }

            statement.setTranspileTarget(transpiler, statementTarget);
        }
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        for (AbstractNode statement : statements)
        {
            MCLError error = statement.transpile(transpiler);
            if (error != null) return error;

            if (statement instanceof BlockDefinitionNode block)
            {
                error = block.transpileStatement(transpiler, mainFunctionPath);
                if (error != null) return error;
            }
        }
        return null;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("BLOCK");

        for (AbstractNode statement : statements) statement.debugPrint(depth + 1);
    }
}
