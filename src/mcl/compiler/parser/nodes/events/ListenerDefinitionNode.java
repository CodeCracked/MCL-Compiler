package mcl.compiler.parser.nodes.events;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.EventSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLListenerParametersError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.LocationNode;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.blocks.BlockDefinitionNode;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class ListenerDefinitionNode extends BlockDefinitionNode
{
    public final LocationNode location;
    public final ParameterListNode parameterList;
    public Path callFunctionFile;

    public ListenerDefinitionNode(Token keyword, LocationNode location, ParameterListNode parameterList, AbstractNode body)
    {
        super(keyword.startPosition(), parameterList.endPosition(), body, location.namespace != null ? String.format("listener_%s_%s", location.namespace.value(), location.identifier.value()) : String.format("listener_%s", location.identifier.value()));
        this.location = location;
        this.parameterList = parameterList;
    }

    @Override
    public void walkChildren(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, location);
        location.walk(parentChildConsumer);

        parentChildConsumer.accept(this, parameterList);
        parameterList.walk(parentChildConsumer);
    }

    @Override
    protected MCLError createContextSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return parameterList.createSymbols(compiler, source);
    }

    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = location.symbolAnalysis(compiler, source, SymbolType.EVENT);
        if (error != null) return error;

        // Check parameter list matches event definition
        EventSymbol event = (EventSymbol) location.getSymbol(compiler, SymbolType.EVENT);
        if (event.parameters.size() != parameterList.parameters.size()) return new MCLListenerParametersError(compiler, this, event);
        for (int i = 0; i < event.parameters.size(); i++) if (!event.parameters.get(i).type.equals(parameterList.parameters.get(i).type)) return new MCLListenerParametersError(compiler, this, event);

        return parameterList.symbolAnalysis(compiler, source);
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target) throws IOException
    {
        super.setTranspileTarget(compiler, target);
        parameterList.setTranspileTarget(compiler, target);

        // Create Call File
        callFunctionFile = transpileTarget.resolve("call.mcfunction");
        callFunctionFile.getParent().toFile().mkdirs();
        callFunctionFile.toFile().createNewFile();

        // Register to Event
        EventSymbol event = (EventSymbol) location.getSymbol(compiler, SymbolType.EVENT);
        event.listenerFunctionFiles.add(callFunctionFile);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        MCLError error = transpiler.pushStacks(callFunctionFile);
        if (error != null) return error;

        error = transpiler.runFunctionFile(callFunctionFile, ((BlockStatementNode)body).mainFunction);
        if (error != null) return error;

        error = transpiler.popStacks(callFunctionFile);
        if (error != null) return error;

        return super.transpile(transpiler);
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_DEFINITION");

        location.debugPrint(depth + 1);
        parameterList.debugPrint(depth + 1);
        body.debugPrint(depth + 1);
    }
}
