package mcl.compiler.transpiler;

import mcl.compiler.CompilerConfig;
import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLFileWriteError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.blocks.NamespaceDefinitionNode;
import mcl.compiler.parser.nodes.blocks.ProgramRootNode;
import mcl.compiler.source.MCLSourceCollection;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class MCLTranspiler
{
    private final MCLSourceCollection source;
    private final MCLCompiler compiler;
    private final ProgramRootNode syntaxTree;

    private final Path rootFolder;
    private final Path dataFolder;

    public MCLTranspiler(MCLSourceCollection source, MCLCompiler compiler, AbstractNode syntaxTree, File target)
    {
        this.source = source;
        this.compiler = compiler;
        this.syntaxTree = (ProgramRootNode) syntaxTree;

        this.rootFolder = target.toPath();
        this.dataFolder = rootFolder.resolve("data");
    }

    public MCLError transpile()
    {
        FileUtils.delete(rootFolder.toFile(), false);
        syntaxTree.walk((parent, child) -> child.parent = parent);

        try
        {
            syntaxTree.setTranspileTarget(rootFolder);
            return syntaxTree.transpile(this);
        }
        catch (IOException e)
        {
            return new MCLFileWriteError(e);
        }
    }

    public MCLError pushStacks(Path target)
    {
        return appendToFile(target, file ->
        {
            file.println(applyConfig("data modify storage {config.variables} CallStack prepend from storage {config.variables} CallStack[0]"));
            file.println(applyConfig("data modify storage {config.variables} ExpressionStack prepend from storage {config.variables} ExpressionStack[0]"));
        });
    }
    public MCLError popStacks(Path target)
    {
        return appendToFile(target, file ->
        {
            file.println(applyConfig("execute if data storage {config.variables} CallStack[1] run data remove storage {config.variables} CallStack[0]"));
            file.println(applyConfig("execute if data storage {config.variables} ExpressionStack[1] run data remove storage {config.variables} ExpressionStack[0]"));
        });
    }

    public String getFunctionName(Path functionFile)
    {
        String fromNamespace = functionFile.toString().replace(dataFolder.toString(), "").trim().substring(1);
        String[] tokens = fromNamespace.split(Pattern.quote(File.separator));

        StringBuilder functionName = new StringBuilder(tokens[0]);
        functionName.append(":");
        for (int i = 2; i < tokens.length - 1; i++) functionName.append(tokens[i] + "/");
        functionName.append(tokens[tokens.length - 1].replace(".mcfunction", ""));

        return functionName.toString();
    }
    public MCLError runFunctionFile(Path target, Path functionFile)
    {
        return appendToFile(target, file -> file.println("function " + getFunctionName(functionFile)));
    }

    public MCLError assignVariable(Path target, VariableSymbol variable, int register)
    {
        return assignVariable(target, variable.tableLocation, variable.type, register);
    }
    public MCLError assignVariable(Path target, String location, RuntimeType type, int register)
    {
        return appendToFile(target, file -> file.println(applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r%s {config.expressions}", location, type.getMinecraftName(), type.scaleDown(compiler.config), register)));
    }

    public MCLError accessVariable(Path target, VariableSymbol variable, int register)
    {
        return accessVariable(target, variable.tableLocation, variable.type, register);
    }
    public MCLError accessVariable(Path target, String location, RuntimeType type, int register)
    {
        return appendToFile(target, file -> file.println(applyConfig("execute store result score r%s {config.expressions} run data get storage {config.variables} CallStack[0].%s %s %s", register, location, type.getMinecraftName(), type.scaleUp(compiler.config))));
    }

    public MCLError assignReturn(Path target, RuntimeType type)
    {
        return appendToFile(target, file -> file.println(applyConfig("execute store result storage {config.variables} CallStack[0].return %s %s run scoreboard players get r0 {config.expressions}", type.getMinecraftName(), type.scaleDown(compiler.config))));
    }

    public MCLError appendToFile(Path target, Consumer<PrintWriter> consumer)
    {
        target.getParent().toFile().mkdirs();

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
            return new MCLFileWriteError(e);
        }
    }
    public String applyConfig(String format, Object... params)
    {
        return String.format(format, params)
                .replace("{config.floatScaleDown}", compiler.config.floatScaleDown())
                .replace("{config.floatScaleUp}", compiler.config.floatScaleUp())
                .replace("{config.expressions}", compiler.config.expressionsObjective())
                .replace("{config.constants}", compiler.config.constantsObjective())
                .replace("{config.variables}", compiler.config.variablesStorage());
    }

    public MCLCompiler getCompiler() { return compiler; }
    public CompilerConfig getConfig() { return compiler.config; }
    public MCLSourceCollection getSource() { return source; }
    public Path getRootFolder() { return rootFolder; }
    public Path getDataFolder() { return dataFolder; }

    public Path getNamespaceFolder(AbstractNode node)
    {
        AbstractNode current = node;
        while (current != null && !(current instanceof NamespaceDefinitionNode)) current = current.parent;

        if (current != null) return dataFolder.resolve((String)((NamespaceDefinitionNode)current).identifier.value());
        else return null;
    }
    public Path getFunctionsFolder(AbstractNode node)
    {
        return getNamespaceFolder(node).resolve("functions");
    }
}
