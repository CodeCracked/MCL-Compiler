package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

import java.nio.file.Path;

public class NamespaceDefinitionNode extends NamedBlockDefinitionNode
{
    public NamespaceDefinitionNode(Token keyword, Token identifier, AbstractNode body)
    {
        super(keyword.startPosition(), body.endPosition(), identifier, body);
    }

    @Override
    protected MCLError createDefinitionSymbol(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().addSymbol(new Symbol(identifier, SymbolType.NAMESPACE));
    }

    @Override
    protected Path getDefinitionFolder(Path target)
    {
        return target.resolve((String)identifier.value()).resolve("functions");
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("NAMESPACE:" + identifier.value());
        body.debugPrint(depth + 1);
    }
}
