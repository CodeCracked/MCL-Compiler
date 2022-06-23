package mcl.compiler;

import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.LexerResult;
import mcl.compiler.lexer.MCLLexer;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.source.MCLSourceCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MCLCompiler
{
    private MCLSourceCollection sourceCollection;

    public void compile(File source, File target) throws IOException, MCLError
    {
        // Generate Tokens
        sourceCollection = new MCLSourceCollection(source);
        MCLLexer lexer = new MCLLexer(this);
        LexerResult lexerResult = lexer.makeTokens();
        if (lexerResult.error() != null) throw lexerResult.error();
        List<Token<?>> tokens = lexerResult.tokens();

        // Debug Print Tokens
        for (int i = 0; i < tokens.size(); i++)
        {
            Token<?> token = tokens.get(i);
            System.out.print(token);

            if (i == tokens.size() - 1 || token.type() == TokenType.END_OF_LINE) System.out.println();
            else System.out.print(", ");
        }
        System.out.println();

        // Generate Abstract Syntax Tree
        MCLParser parser = new MCLParser(this, tokens);
        AbstractNode ast = parser.parse();

        // Debug Print AST
        System.out.println(ast);
    }

    public MCLSourceCollection getSource() { return sourceCollection; }
}
