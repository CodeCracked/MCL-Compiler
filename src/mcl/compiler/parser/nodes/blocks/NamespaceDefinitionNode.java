package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.symbols.NamespaceSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.FileUtils;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Files;
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
        return compiler.getSymbolTable().addSymbol(new NamespaceSymbol(identifier));
    }

    @Override
    protected Path getDefinitionFolder(Path target)
    {
        return target.resolve((String)identifier.value()).resolve("functions");
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        MCLError error = super.transpile(transpiler);
        if (error != null) return error;

        if (blocks[0] instanceof BlockStatementNode block && block.mainFunctionPath.toFile().exists())
        {
            if (Files.readString(block.mainFunctionPath).trim().length() == 0) block.mainFunctionPath.toFile().delete();
            if (block.mainFunctionPath.getParent().toFile().listFiles().length == 0) FileUtils.delete(block.mainFunctionPath.getParent().toFile(), true);
        }
        return null;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("NAMESPACE:" + identifier.value());
        blocks[0].debugPrint(depth + 1);
    }
}
