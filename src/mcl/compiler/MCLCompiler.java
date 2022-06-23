package mcl.compiler;

import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.LexerResult;
import mcl.compiler.lexer.MCLLexer;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.source.MCLSourceCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MCLCompiler
{
    public void compile(File source, File target) throws IOException, MCLError
    {
        // Generate Tokens
        MCLSourceCollection sourceCollection = new MCLSourceCollection(source);
        MCLLexer lexer = new MCLLexer(sourceCollection);
        LexerResult lexerResult = lexer.makeTokens();
        if (lexerResult.error() != null) throw lexerResult.error();
        List<Token<?>> tokens = lexerResult.tokens();

        // Debug Print Tokens
        for (int i = 0; i < tokens.size(); i++)
        {
            Token<?> token = tokens.get(i);
            System.out.print(token);

            if (i == tokens.size() - 1 || token.type() == TokenType.NEWLINE) System.out.println();
            else System.out.print(", ");
        }
        System.out.println();

        // Generate Abstract Syntax Tree
        MCLParser parser = new MCLParser(sourceCollection, tokens);
        ParseResult parseResult = parser.parse();
        if (parseResult.error() != null) throw parseResult.error();
        AbstractNode ast = parseResult.node();

        // Debug Print AST
        ast.debugPrint(0);
    }
}
