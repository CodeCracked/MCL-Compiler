package mcl.compiler.analyzer;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLFileWriteError;
import mcl.compiler.lexer.LexerResult;
import mcl.compiler.lexer.MCLLexer;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.parser.nodes.blocks.ProgramRootNode;
import mcl.compiler.source.MCLSourceCollection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MCLSemanticAnalyzer
{
    private final MCLCompiler compiler;
    private final MCLSourceCollection source;
    private final ProgramRootNode syntaxTree;

    public MCLSemanticAnalyzer(MCLCompiler compiler, MCLSourceCollection source, ProgramRootNode syntaxTree)
    {
        this.compiler = compiler;
        this.source = source;
        this.syntaxTree = syntaxTree;
    }

    public MCLError analyze()
    {
        MCLError error;

        for (Path header : source.getHeaders())
        {
            error = loadHeader(header);
            if (error != null) return error;
        }

        error = syntaxTree.createSymbols(compiler, source);
        if (error != null) return error;

        return syntaxTree.symbolAnalysis(compiler, source);
    }

    private MCLError loadHeader(Path header)
    {
        try
        {
            compiler.getSource().openHeader = header;

            // Construct Source Collection
            MCLSourceCollection headerSource = new MCLSourceCollection(header.toFile(), false);

            // Generate Tokens From Source Collection
            MCLLexer lexer = new MCLLexer(headerSource);
            LexerResult lexerResult = lexer.makeTokens();
            if (lexerResult.error() != null) return lexerResult.error();
            List<Token> tokens = lexerResult.tokens();

            // Parse Tokens Into Syntax Tree
            MCLParser parser = new MCLParser(compiler, headerSource, tokens);
            ParseResult parseResult = parser.parse();
            if (parseResult.error() != null) return parseResult.error();
            ProgramRootNode headerSyntaxTree = (ProgramRootNode)parseResult.node();

            // Add Header Syntax Tree to Program Syntax Tree
            syntaxTree.addHeaderSyntaxTree(header, headerSyntaxTree);

            compiler.getSource().openHeader = null;
            return null;
        }
        catch (IOException e)
        {
            return new MCLFileWriteError(e);
        }
    }
}
