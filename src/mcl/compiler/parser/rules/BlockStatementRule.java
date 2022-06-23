package mcl.compiler.parser.rules;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;

import java.util.ArrayList;
import java.util.List;

public class BlockStatementRule implements GrammarRule
{
    private int requiredIndent;

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

        while (parser.getCurrentToken().type() == TokenType.NEWLINE)
        {
            result.registerAdvancement();
            parser.advance();
            if (parser.getCurrentToken().type() != TokenType.INDENT) break;

            int indent = (int)parser.getCurrentToken().value();
            if (indent < requiredIndent) break;
            else if (indent > requiredIndent) return result.failure(new MCLSyntaxError(parser, "Expected indent of size " + requiredIndent));

            result.registerAdvancement();
            parser.advance();

            AbstractNode statement = result.register(GrammarRules.EXPRESSION.build(parser));
            if (result.error() != null) return result;

            statements.add(statement);
        }

        return result.success(new BlockStatementNode(statements, start));
    }
}
