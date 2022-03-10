package mcl.compiler.lexer;

import mcl.compiler.MCLSourceCollection;
import mcl.compiler.exceptions.MCLLexicalException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer
{
    private static final Pattern SPACES = Pattern.compile("^ *");

    public List<Token> tokenize(MCLSourceCollection sourceCollection) throws MCLLexicalException
    {
        System.out.println("Lexical Analysis:");
        List<Token> tokens = new ArrayList<>();
        String source = sourceCollection.getSource();

        int line = 0;
        int currentChar = 0;
        int before = source.length();
        while (before > 0)
        {
            for (TokenType tokenType : TokenType.values())
            {
                Matcher matcher = tokenType.getRegex().matcher(source);
                if (matcher.find() && matcher.start() == 0)
                {
                    String tokenContent = matcher.group();
                    var lineLocation = sourceCollection.getLineLocation(line);
                    tokens.add(new Token(tokenType, tokenContent, lineLocation.getFile(), lineLocation.getLine(), currentChar - lineLocation.getStart()));

                    source = source.substring(matcher.end());
                    currentChar += matcher.end();

                    Matcher spaceRemover = SPACES.matcher(source);
                    if (spaceRemover.find() && spaceRemover.start() == 0)
                    {
                        source = source.substring(spaceRemover.end());
                        currentChar += spaceRemover.end();
                    }

                    if (tokenType == TokenType.END_OF_LINE) line++;
                    break;
                }
            }

            if (source.length() >= before)
            {
                int nextSpace = source.indexOf(' ');
                String token = nextSpace > 0 ? source.substring(0, nextSpace) : source;
                throw new MCLLexicalException(token);
            }
            else before = source.length();
        }

        var lineLocation = sourceCollection.getLastLineLocation();
        tokens.add(new Token(TokenType.END_OF_LINE, "\n", lineLocation.getFile(), lineLocation.getLine(), currentChar - lineLocation.getStart()));
        tokens.forEach(token ->
        {
            if (token.getTokenType() == TokenType.END_OF_LINE) System.out.println(token);
            else
            {
                System.out.print(token);
                System.out.print(' ');
            }
        });
        System.out.println();
        return tokens;
    }
}
