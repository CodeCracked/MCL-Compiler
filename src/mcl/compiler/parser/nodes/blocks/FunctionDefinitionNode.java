package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;
import mcl.compiler.source.MCLSourceCollection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class FunctionDefinitionNode extends NamedBlockDefinitionNode
{
    public final List<VariableSignatureNode> parameters;
    public final RuntimeType returnType;
    public final FunctionSymbol symbol;

    public FunctionDefinitionNode(Token keyword, Token identifier, List<VariableSignatureNode> parameters, Token returnType, BlockStatementNode body)
    {
        super(keyword.startPosition(), body.endPosition(), identifier, body);

        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType != null ? RuntimeType.parse((String)returnType.value()) : RuntimeType.VOID;

        List<VariableSymbol> parameterSymbols = new ArrayList<>();
        for (VariableSignatureNode parameter : this.parameters) parameterSymbols.add(parameter.symbol);
        this.symbol = new FunctionSymbol(identifier, parameterSymbols, this.returnType);
    }

    @Override
    public void walkChildren(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            parentChildConsumer.accept(this, parameter);
            parameter.walk(parentChildConsumer);
        }
    }

    @Override
    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().addSymbol(symbol);
    }
    @Override
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            MCLError error = parameter.createSymbols(compiler, source);
            if (error != null) return error;
        }
        return null;
    }

    @Override
    public void setTranspileTarget(Path target) throws IOException
    {
        super.setTranspileTarget(target);
        symbol.mainFunctionFile = ((BlockStatementNode)body).mainFunction;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_DEFINITION");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        System.out.print("  ".repeat(depth + 1));
        System.out.println("RETURN:" + returnType.toString());

        for (VariableSignatureNode parameter : parameters) parameter.debugPrint(depth + 1);
        body.debugPrint(depth + 1);
    }
}
