package mcl.compiler.parser.rules.blocks;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;

import java.util.ArrayList;
import java.util.List;

public class BlockStatementRule implements GrammarRule
{
    private final int requiredIndent;

    public BlockStatementRule(int requiredIndent)
    {
        this.requiredIndent = requiredIndent;
    }

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        List<AbstractNode> statements = new ArrayList<>();
        int start = parser.getCurrentToken().startPosition();

        if (parser.getCurrentToken().type() == TokenType.NEWLINE)
        {
            while (parser.getCurrentToken().type() == TokenType.NEWLINE && parser.peekNextToken().type() == TokenType.INDENT)
            {
                // Check Indent
                int indent = (int)parser.peekNextToken().value();
                if (indent < requiredIndent) break;
                else if (indent > requiredIndent) return result.failure(new MCLSyntaxError(parser.getSource(), parser.peekNextToken(), "Expected indent of size " + requiredIndent));

                // Clear Newline and Indent
                result.registerAdvancement();
                parser.advance();
                result.registerAdvancement();
                parser.advance();

                // Build Statement
                AbstractNode statement = result.register(GrammarRules.STATEMENT.build(parser));
                if (result.error() != null) return result;

                statements.add(statement);
            }
        }
        else if (parser.getCurrentToken().type() != TokenType.EOF)
        {
            AbstractNode inlineStatement = result.register(GrammarRules.STATEMENT.build(parser));
            if (result.error() != null) return result;
            statements.add(inlineStatement);
        }

        return result.success(new BlockStatementNode(statements, start));
    }
}