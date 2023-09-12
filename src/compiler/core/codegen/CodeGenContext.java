package compiler.core.codegen;

import compiler.core.parser.AbstractNode;
import compiler.core.util.IO;
import compiler.core.util.Result;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class CodeGenContext
{
    private final CodeGenerator generator;
    private final Path rootPath;
    private Path currentDirectory;
    private int pathDepth;
    private Optional<PrintWriter> writer;
    
    public CodeGenContext(CodeGenerator generator, Path rootPath)
    {
        this.generator = generator;
        this.rootPath = rootPath;
        this.currentDirectory = rootPath;
        this.pathDepth = 0;
        this.writer = Optional.empty();
    }
    
    public CodeGenerator getCodeGenerator() { return generator; }
    public Path getRootPath() { return rootPath; }
    public Path getCurrentDirectory() { return currentDirectory; }
    public Optional<PrintWriter> getOpenFile() { return writer; }
    
    //region Generation
    public <T extends AbstractNode> Result<Void> generate(T node) { return generator.generate(node, this); }
    //endregion
    //region Files
    public void writeFile(String name, Consumer<PrintWriter> writeConsumer) throws IOException { writeFile(name, writeConsumer, true); }
    public void writeFile(String name, Consumer<PrintWriter> writerConsumer, boolean append) throws IOException
    {
        openFile(name, append);
        writerConsumer.accept(writer.get());
        closeFile();
    }
    
    public void openFile(String name) throws IOException { openFile(name, true); }
    public void openFile(String name, boolean append) throws IOException
    {
        if (this.writer.isPresent())
        {
            IO.Debug.println("Codegen Warning: Trying to open a new file when an existing one isn't closed!");
            closeFile();
        }
        
        File file = currentDirectory.resolve(name).toFile();
        this.writer = Optional.of(new PrintWriter(new BufferedWriter(new FileWriter(file, append))));
    }
    public void closeFile()
    {
        if (this.writer.isPresent())
        {
            this.writer.get().flush();
            this.writer.get().close();
            this.writer = Optional.empty();
        }
        else IO.Debug.println("Codegen Warning: Trying to close a file when none are opened!");
    }
    //endregion
    //region Directories
    public void openSubdirectory(String name)
    {
        if (this.writer.isPresent())
        {
            IO.Debug.println("Codegen Warning: Trying to open a subdirectory when a file is opened!");
            closeFile();
        }
        
        this.currentDirectory = this.currentDirectory.resolve(name);
        this.currentDirectory.toFile().mkdirs();
        this.pathDepth++;
    }
    public void closeSubdirectory()
    {
        if (this.pathDepth > 0)
        {
            if (this.writer.isPresent())
            {
                IO.Debug.println("Codegen Warning: Trying to close a close a subdirectory when a file is opened!");
                closeFile();
            }
            
            this.currentDirectory = this.currentDirectory.getParent();
            this.pathDepth--;
        }
        else IO.Debug.println("Codegen Warning: Trying to close a subdirectory when none are opened!");
    }
    //endregion
}
