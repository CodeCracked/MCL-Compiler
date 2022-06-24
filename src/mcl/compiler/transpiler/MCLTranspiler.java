package mcl.compiler.transpiler;

import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLFileAppendError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.blocks.NamespaceDefinitionNode;
import mcl.compiler.parser.nodes.blocks.ProgramRootNode;
import mcl.compiler.source.MCLSourceCollection;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Consumer;

public class MCLTranspiler
{
    private final MCLSourceCollection source;
    private final ProgramRootNode syntaxTree;

    private final Path rootFolder;
    private final Path dataFolder;

    public MCLTranspiler(MCLSourceCollection source, AbstractNode syntaxTree, File target)
    {
        this.source = source;
        this.syntaxTree = (ProgramRootNode) syntaxTree;

        this.rootFolder = target.toPath();
        this.dataFolder = rootFolder.resolve("data");
    }

    public MCLError transpile()
    {
        FileUtils.delete(rootFolder.toFile(), false);
        syntaxTree.walk((parent, child) -> child.parent = parent);
        return syntaxTree.transpile(this, rootFolder);
    }

    public MCLError appendToFile(Path target, Consumer<PrintWriter> consumer)
    {
        try(FileWriter fileWriter = new FileWriter(target.toFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bufferedWriter))
        {
            consumer.accept(out);
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new MCLFileAppendError(e);
        }
    }

    public Path getRootFolder() { return rootFolder; }
    public Path getDataFolder() { return dataFolder; }
    public Path getNamespaceFolder(AbstractNode node)
    {
        AbstractNode current = node;
        while (current != null && !(current instanceof NamespaceDefinitionNode)) current = current.parent;

        if (current != null) return dataFolder.resolve((String)((NamespaceDefinitionNode)current).identifier.value());
        else return null;
    }
}
