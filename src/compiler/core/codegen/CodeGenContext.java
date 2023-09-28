package compiler.core.codegen;

import compiler.core.parser.AbstractNode;
import compiler.core.util.IO;
import compiler.core.util.Result;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;

public class CodeGenContext
{
    private record Snapshot(Path currentDirectory, int pathDepth, Path[] filePaths)
    {
        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Snapshot snapshot = (Snapshot) o;
            return pathDepth == snapshot.pathDepth && Objects.equals(currentDirectory, snapshot.currentDirectory) && Arrays.equals(filePaths, snapshot.filePaths);
        }
    
        @Override
        public int hashCode()
        {
            int result = Objects.hash(currentDirectory, pathDepth);
            result = 31 * result + Arrays.hashCode(filePaths);
            return result;
        }
    }
    
    private final CodeGenerator generator;
    private final Path rootPath;
    private Path currentDirectory;
    private int pathDepth;
    private final Stack<Path> filePathStack;
    private final Stack<PrintWriter> fileWriterStack;
    private final Stack<Snapshot> snapshotStack;
    
    public CodeGenContext(CodeGenerator generator, Path rootPath)
    {
        this.generator = generator;
        this.rootPath = rootPath;
        this.currentDirectory = rootPath;
        this.pathDepth = 0;
        this.filePathStack = new Stack<>();
        this.fileWriterStack = new Stack<>();
        this.snapshotStack = new Stack<>();
        openSnapshot();
    }
    
    public CodeGenerator getCodeGenerator() { return generator; }
    public Path getRootPath() { return rootPath; }
    public Path getCurrentDirectory() { return currentDirectory; }
    public Optional<PrintWriter> getOpenFile() { return fileWriterStack.isEmpty() ? Optional.empty() : Optional.ofNullable(fileWriterStack.peek()); }
    
    //region Snapshots
    void openSnapshot()
    {
        Snapshot snapshot = new Snapshot(currentDirectory, pathDepth, filePathStack.toArray(Path[]::new));
        snapshotStack.push(snapshot);
    }
    void closeSnapshot()
    {
        if (snapshotStack.size() <= 1)
        {
            IO.Warnings.println("Trying to close a CodeGenContext snapshot when none are opened!");
            return;
        }
        
        Snapshot restore = snapshotStack.pop();
        this.currentDirectory = restore.currentDirectory;
        this.pathDepth = restore.pathDepth;
        while (fileWriterStack.size() > restore.filePaths.length)
        {
            filePathStack.pop();
            PrintWriter writer = fileWriterStack.pop();
            if (writer != null)
            {
                writer.flush();
                writer.close();
            }
        }
    }
    //endregion
    //region Generation
    public <T extends AbstractNode> Result<Void> generate(T node) { return generator.generate(node, this); }
    //endregion
    //region Files
    public void writeFile(String name, Consumer<PrintWriter> writeConsumer) throws IOException { writeFile(name, writeConsumer, true); }
    public void writeFile(String name, Consumer<PrintWriter> writerConsumer, boolean append) throws IOException
    {
        openFile(name, append);
        writerConsumer.accept(fileWriterStack.peek());
        closeFile();
    }
    
    public void openFile(String name) throws IOException { openFile(name, true); }
    public void openFile(String name, boolean append) throws IOException
    {
        Path path = currentDirectory.resolve(name);
        File file = path.toFile();
        
        PrintWriter fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        this.filePathStack.push(path);
        this.fileWriterStack.push(fileWriter);
    }
    public void closeFile()
    {
        if (fileWriterStack.size() > snapshotStack.peek().filePaths.length)
        {
            filePathStack.pop();
            PrintWriter fileWriter = fileWriterStack.pop();
            fileWriter.flush();
            fileWriter.close();
        }
        else IO.Debug.println("Codegen Warning: Trying to close a file when none are opened!");
    }
    //endregion
    //region Directories
    public void openSubdirectory(String... relativePath)
    {
        for (String pathElement : relativePath)
        {
            if (pathElement.equals("..")) closeSubdirectory();
            else
            {
                this.currentDirectory = this.currentDirectory.resolve(pathElement);
                this.currentDirectory.toFile().mkdirs();
                this.pathDepth++;
            }
        }
    }
    public void closeSubdirectory(int levels)
    {
        for (int i = 0; i < levels; i++) closeSubdirectory();
    }
    public void closeSubdirectory()
    {
        if (this.pathDepth > snapshotStack.peek().pathDepth)
        {
            this.currentDirectory = this.currentDirectory.getParent();
            this.pathDepth--;
        }
        else IO.Debug.println("Codegen Warning: Trying to close a subdirectory when none are opened in this snapshot!");
    }
    //endregion
}
