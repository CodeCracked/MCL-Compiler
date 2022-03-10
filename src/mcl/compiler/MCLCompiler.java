package mcl.compiler;

import mcl.compiler.exceptions.MCLException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.LexicalAnalyzer;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.ParseTree;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MCLCompiler
{
    private SyntaxAnalyzer syntaxAnalyzer;

    public void compile(File source, File target) throws IOException, MCLException
    {
        MCLSourceCollection sourceCollection = new MCLSourceCollection(source);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();

        List<Token> tokens = lexicalAnalyzer.tokenize(sourceCollection);
        ParseTree parseTree = syntaxAnalyzer.constructParseTree(tokens);
    }
}
