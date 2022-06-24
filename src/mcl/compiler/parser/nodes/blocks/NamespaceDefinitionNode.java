package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class NamespaceDefinitionNode extends AbstractNode
{
    public final Token identifier;
    public final AbstractNode body;

    public NamespaceDefinitionNode(Token keyword, Token identifier, AbstractNode body)
    {
        super(keyword.startPosition(), body.endPosition());
        this.identifier = identifier;
        this.body = body;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, body);
        body.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = compiler.getSymbolTable().addSymbol(new Symbol(identifier, SymbolType.NAMESPACE));
        if (error != null) return error;
        return body.createSymbols(compiler, source);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        return body.transpile(transpiler, target);
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
        System.out.println("NAMESPACE:" + identifier.value());
        body.debugPrint(depth + 1);
    }
}
