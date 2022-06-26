package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLUndefinedNamespaceError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.blocks.NamespaceDefinitionNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.BiConsumer;

public class LocationNode extends AbstractNode
{
    public final Token namespace;
    public final Token identifier;

    public LocationNode(Token first, Token second)
    {
        super(first.startPosition(), second != null ? second.endPosition() : first.endPosition());

        if (second != null)
        {
            this.namespace = first;
            this.identifier = second;
        }
        else
        {
            this.namespace = null;
            this.identifier = first;
        }
    }

    public Symbol getSymbol(MCLCompiler compiler, SymbolType symbolType)
    {
        if (namespace == null) return compiler.getSymbolTable().getSymbol((String)identifier.value(), symbolType);
        else
        {
            UUID namespaceTableID = compiler.getSyntaxTree().getNamespaceNode((String)namespace.value()).symbolTableID;
            return compiler.getRootSymbolTable().getOrCreateChildTable(namespaceTableID).getSymbol((String)identifier.value(), symbolType);
        }
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) { }

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
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source, SymbolType symbolType)
    {
        if (namespace == null) return compiler.getSymbolTable().checkSymbolDefinition(identifier, symbolType);
        else
        {
            NamespaceDefinitionNode namespaceNode = compiler.getSyntaxTree().getNamespaceNode((String)namespace.value());
            if (namespaceNode == null) return new MCLUndefinedNamespaceError(source, namespace);

            MCLError error = compiler.getRootSymbolTable().checkSymbolDefinition(namespace, SymbolType.NAMESPACE);
            if (error != null) return error;

            return compiler.getRootSymbolTable().getOrCreateChildTable(namespaceNode.symbolTableID).checkSymbolDefinition(identifier, symbolType);
        }
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = target;
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
        System.out.println("LOCATION:" + this);
    }

    @Override
    public String toString()
    {
        String str = "LOCATION:" + identifier.value();
        if (namespace != null) str += "(in " + namespace.value() + ")";
        return str;
    }
}
