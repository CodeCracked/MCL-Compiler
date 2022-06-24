package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.variables.VariableDefinitionNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class NamespaceBodyNode extends AbstractNode
{
    public final List<AbstractNode> definitions;

    public NamespaceBodyNode(List<AbstractNode> definitions, int start)
    {
        super(start, definitions.size() > 0 ? definitions.get(definitions.size() - 1).endPosition() : start + 1);
        this.definitions = Collections.unmodifiableList(definitions);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (AbstractNode definition : definitions)
        {
            parentChildConsumer.accept(this, definition);
            definition.walk(parentChildConsumer);
        }
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error;

        for (AbstractNode definition : definitions)
        {
            error = definition.createSymbols(compiler, source);
            if (error != null) return error;
        }

        return null;
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        MCLError error;

        Path functionsFolder = transpiler.getFunctionsFolder(this);
        Path loadFile = functionsFolder.resolve("load.mcfunction");

        for (AbstractNode definition : definitions)
        {
            if (definition instanceof VariableDefinitionNode variableDefinition)
            {
                error = variableDefinition.transpile(transpiler, loadFile);
                if (error != null) return error;
            }
            else if (definition instanceof FunctionDefinitionNode functionDefinitionNode)
            {
                error = functionDefinitionNode.transpile(transpiler, functionsFolder);
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
        System.out.println("NAMESPACE_BODY");

        for (AbstractNode definition : definitions) definition.debugPrint(depth + 1);
    }
}
