package mcl.compiler;

import mcl.compiler.analyzer.MCLSemanticAnalyzer;
import mcl.compiler.analyzer.SymbolTable;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.LexerResult;
import mcl.compiler.lexer.MCLLexer;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.FileUtils;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MCLCompiler
{
    public final CompilerConfig config;

    private SymbolTable rootSymbolTable;
    private SymbolTable currentSymbolTable;

    public MCLCompiler(CompilerConfig config)
    {
        MCLLexer.init();
        this.config = config;
    }

    public void compile(File source, File target) throws IOException, MCLError
    {
        // Setup
        MCLSourceCollection sourceCollection = new MCLSourceCollection(source);
        rootSymbolTable = new SymbolTable(sourceCollection, null, UUID.randomUUID()).addRootSymbols();
        currentSymbolTable = rootSymbolTable;

        // Generate Tokens
        MCLLexer lexer = new MCLLexer(sourceCollection);
        LexerResult lexerResult = lexer.makeTokens();
        if (lexerResult.error() != null) throw lexerResult.error();
        List<Token> tokens = lexerResult.tokens();

        // Debug Print Tokens
        for (int i = 0; i < tokens.size(); i++)
        {
            Token token = tokens.get(i);
            System.out.print(token);

            if (i == tokens.size() - 1 || token.type() == TokenType.NEWLINE) System.out.println();
            else System.out.print(", ");
        }
        System.out.println();

        // Generate Abstract Syntax Tree
        MCLParser parser = new MCLParser(sourceCollection, tokens);
        ParseResult parseResult = parser.parse();
        if (parseResult.error() != null) throw parseResult.error();
        AbstractNode syntaxTree = parseResult.node();

        // Debug Print AST
        syntaxTree.debugPrint(0);
        System.out.println();

        // Perform Symbol Analysis
        MCLSemanticAnalyzer semanticAnalyzer = new MCLSemanticAnalyzer(this, sourceCollection, syntaxTree);
        MCLError symbolsError = semanticAnalyzer.loadSymbolTables();
        if (symbolsError != null) throw symbolsError;

        // Transpile AST into Minecraft Function Files
        MCLTranspiler transpiler = new MCLTranspiler(sourceCollection, this, syntaxTree, target);
        MCLError transpileError = transpiler.transpile();
        if (transpileError != null)
        {
            FileUtils.delete(target, false);
            throw transpileError;
        }
    }

    public void pushSymbolTable(UUID id)
    {
        currentSymbolTable = currentSymbolTable.getOrCreateChildTable(id);
    }
    public void popSymbolTable()
    {
        if (currentSymbolTable.parent != null) currentSymbolTable = currentSymbolTable.parent;
    }
    public SymbolTable getRootSymbolTable() { return rootSymbolTable; }
    public SymbolTable getSymbolTable() { return currentSymbolTable; }
}
