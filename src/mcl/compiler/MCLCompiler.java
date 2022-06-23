package mcl.compiler;

import mcl.compiler.exceptions.MCLException;
import mcl.compiler.lexer.MCLLexer;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MCLCompiler
{
    public void compile(File source, File target) throws IOException, MCLException
    {
        MCLSourceCollection sourceCollection = new MCLSourceCollection(source);
        MCLLexer lexer = new MCLLexer(sourceCollection);

        List<Token<?>> tokens = lexer.makeTokens();
        for (int i = 0; i < tokens.size(); i++)
        {
            Token<?> token = tokens.get(i);
            System.out.print(token);

            if (i == tokens.size() - 1 || token.type() == TokenType.END_OF_LINE) System.out.println();
            else System.out.print(", ");
        }
        System.out.println();
    }
}
