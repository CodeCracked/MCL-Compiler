package mcl.compiler.parser.rules.statements;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.GrammarRule;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;
import mcl.compiler.parser.nodes.statements.RunCommandsNode;

import java.util.ArrayList;
import java.util.List;

public class RunCommandsRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Keyword
        Token keyword = parser.getCurrentToken();
        if (!keyword.isKeyword(MCLKeywords.RUN)) return result.failure(new MCLSyntaxError(parser, "Expected 'run'"));
        result.registerAdvancement();
        parser.advance();

        List<Token> commands = new ArrayList<>();

        // Optional Inline Command
        if (parser.getCurrentToken().type() == TokenType.STRING)
        {
            commands.add(parser.getCurrentToken());
            result.registerAdvancement();
            parser.advance();
        }

        // Read Statements
        int blockIndent = parser.getCurrentIndent() + 1;
        while (parser.getCurrentToken().type() == TokenType.NEWLINE)
        {
            int index = parser.getTokenIndex();

            // Clear Newline
            result.registerAdvancement();
            parser.advance();

            // Check Indent
            Token indent = parser.getCurrentToken();
            if (indent.type() != TokenType.INDENT || (Integer)indent.value() < blockIndent)
            {
                parser.setIndex(index);
                break;
            }
            else if ((Integer)indent.value() > blockIndent) return result.failure(new MCLSyntaxError(parser, "Expected indent of size " + blockIndent));
            else
            {
                result.registerAdvancement();
                parser.advance();
            }

            // Get Command
            Token command = parser.getCurrentToken();
            if (command.type() != TokenType.STRING) return result.failure(new MCLSyntaxError(parser, "Expected string"));
            result.registerAdvancement();
            parser.advance();

            commands.add(command);
        }

        if (commands.size() == 0) return result.failure(new MCLSyntaxError(parser, "Expected string"));
        else return result.success(new RunCommandsNode(keyword, commands));
    }
}
