package compiler.core;

import compiler.core.codegen.CodeGenerator;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.RootNode;
import compiler.core.source.SourceCollection;
import compiler.core.util.IO;
import compiler.core.util.Result;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Compiler
{
    private final Lexer lexer;
    private final Parser parser;
    private final CodeGenerator codeGenerator;
    private final boolean debug;
    
    public Compiler(Lexer lexer, Parser parser, CodeGenerator codeGenerator, boolean debug)
    {
        this.lexer = lexer;
        this.parser = parser;
        this.codeGenerator = codeGenerator;
        this.debug = debug;
    }
    
    public Result<Void> compile(Path source, Path destination) throws IOException { return compile(SourceCollection.fromDirectory(source), destination); }
    public Result<Void> compile(SourceCollection source, Path destination)
    {
        Result<Void> result = new Result<>();
        
        // Lexical Analysis
        Result<List<Token>[]> tokens = result.registerIssues(lexer.tokenize(source));
        if (result.getFailure() != null) return result;
        if (debug)
        {
            for (List<Token> file : tokens.get())
            {
                file.forEach(IO.Debug::println);
                IO.Debug.println();
            }
            IO.Debug.println();
        }
        
        // Parsing
        Result<RootNode> ast = result.registerIssues(parser.parse(tokens.get()));
        if (debug && ast.get() != null)
        {
            ast.get().debugPrint(0);
            IO.Debug.println();
        }
        if (result.getFailure() != null) return result;
        
        // Code Generation
        if (codeGenerator != null) result.registerIssues(codeGenerator.generate(ast.get(), destination));
        return result;
    }
}
