package mcl.compiler.parser.nodes.events;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.EventSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.ParameterListNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EventDefinitionNode extends AbstractNode
{
    public final Token identifier;
    public final ParameterListNode parameterList;
    public final EventSymbol event;

    public EventDefinitionNode(Token keyword, Token identifier, ParameterListNode parameterList)
    {
        super(keyword.startPosition(), parameterList.endPosition());

        this.identifier = identifier;
        this.parameterList = parameterList;

        List<VariableSymbol> parameterSymbols = new ArrayList<>();
        for (VariableSignatureNode parameter : this.parameterList.parameters) parameterSymbols.add(parameter.symbol);
        this.event = new EventSymbol(identifier, parameterSymbols);
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, parameterList);
        parameterList.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().addSymbol(event);
        // Don't create parameterList symbols because they are only used on a per-listener basis.
        // The parameterList node only defines the types required for listener symbol analysis
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        return parameterList.symbolAnalysis(compiler, source);
    }

    @Override
    public void setTranspileTarget(Path target) throws IOException
    {
        transpileTarget = new File(target.getParent().toString().replace("functions", "tags" + File.separator + "functions")).toPath().resolve(identifier.value() + ".json");
        transpileTarget.getParent().toFile().mkdirs();
        transpileTarget.toFile().createNewFile();
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        return transpiler.appendToFile(transpileTarget, file ->
        {
            file.println("{");
            file.println("    \"values\": [");

            for (int i = 0; i < event.listenerFunctionFiles.size(); i++)
            {
                file.print("        \"");
                file.print(transpiler.getFunctionName(event.listenerFunctionFiles.get(i)));
                file.print("\"");
                if (i < event.listenerFunctionFiles.size() - 1) file.println(",");
                else file.println();
            }

            file.println("    ]");
            file.println("}");
        });
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
        System.out.println("EVENT_DEFINITION");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        parameterList.debugPrint(depth + 1);
    }
}
