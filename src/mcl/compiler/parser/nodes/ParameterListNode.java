package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class ParameterListNode extends AbstractNode
{
    public final List<VariableSignatureNode> parameters;

    public ParameterListNode(Token openToken, List<VariableSignatureNode> parameters, Token closeToken)
    {
        super(openToken.startPosition(), closeToken.endPosition());
        this.parameters = Collections.unmodifiableList(parameters);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            parentChildConsumer.accept(this, parameter);
            parameter.walk(parentChildConsumer);
        }
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            MCLError error = parameter.createSymbols(compiler, source);
            if (error != null) return error;
        }
        return null;
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            MCLError error = parameter.symbolAnalysis(compiler, source);
            if (error != null) return error;
        }
        return null;
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        for (VariableSignatureNode parameter : parameters) parameter.setTranspileTarget(compiler, target);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
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
        System.out.println("PAR_LIST");
        for (VariableSignatureNode parameter : parameters) parameter.debugPrint(depth + 1);
    }
}
