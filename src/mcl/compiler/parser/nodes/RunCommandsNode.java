package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class RunCommandsNode extends AbstractNode
{
    public final List<Token> commands;

    public RunCommandsNode(Token keyword, List<Token> commands)
    {
        super(keyword.startPosition(), commands.get(commands.size() - 1).endPosition());
        this.commands = Collections.unmodifiableList(commands);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {

    }

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
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = target;
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        for (Token command : commands)
        {
            MCLError error = transpiler.appendToFile(transpileTarget, file -> file.println(command.value()));
            if (error != null) return error;
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
        System.out.println("RUN");

        for (Token command : commands)
        {
            System.out.print("  ".repeat(depth + 1));
            System.out.println(command);
        }
    }
}
