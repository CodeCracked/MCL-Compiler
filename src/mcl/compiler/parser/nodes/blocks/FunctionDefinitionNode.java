package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class FunctionDefinitionNode extends NamedBlockDefinitionNode
{
    public final ParameterListNode parameterList;
    public final RuntimeType returnType;
    public final FunctionSymbol symbol;

    public FunctionDefinitionNode(Token keyword, Token identifier, ParameterListNode parameterList, Token returnType, BlockStatementNode body)
    {
        super(keyword.startPosition(), body.endPosition(), identifier, body);

        this.parameterList = parameterList;
        this.returnType = returnType != null ? RuntimeType.parse((String)returnType.value()) : RuntimeType.VOID;

        List<VariableSymbol> parameterSymbols = new ArrayList<>();
        for (VariableSignatureNode parameter : this.parameterList.parameters) parameterSymbols.add(parameter.symbol);
        this.symbol = new FunctionSymbol(identifier, parameterSymbols, this.returnType);
    }

    @Override
    public void walkChildren(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, parameterList);
        parameterList.walk(parentChildConsumer);
    }

    @Override
    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().addSymbol(symbol);
    }
    @Override
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return parameterList.createSymbols(compiler, source);
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        super.setTranspileTarget(transpiler, target);
        parameterList.setTranspileTarget(transpiler, target);
        symbol.mainFunctionFile = ((BlockStatementNode) blocks[0]).mainFunctionPath;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_DEFINITION");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        parameterList.debugPrint(depth + 1);

        System.out.print("  ".repeat(depth + 1));
        System.out.println("RETURN:" + returnType.toString());

        blocks[0].debugPrint(depth + 1);
    }
}
