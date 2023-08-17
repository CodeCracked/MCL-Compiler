package compiler.core;

import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.Parser;
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
    private final boolean debug;
    
    public Compiler(Lexer lexer, Parser parser, boolean debug)
    {
        this.lexer = lexer;
        this.parser = parser;
        this.debug = debug;
    }
    
    public Result<Void> compile(Path source, Path destination) throws IOException { return compile(SourceCollection.fromDirectory(source), destination); }
    public Result<Void> compile(SourceCollection source, Path destination)
    {
        Result<Void> result = new Result<>();
        
        // Lexical Analysis
        Result<List<Token>> tokens = result.registerIssues(lexer.tokenize(source));
        if (result.getFailure() != null) return result;
        if (debug)
        {
            tokens.get().forEach(IO.Debug::println);
            IO.Debug.println();
        }
        
        // Parsing
        Result<? extends AbstractNode> ast = result.registerIssues(parser.parse(tokens.get()));
        if (result.getFailure() != null) return result;
        if (debug)
        {
            ast.get().debugPrint(0);
            IO.Debug.println();
        }
        
        return result;
    }
}
