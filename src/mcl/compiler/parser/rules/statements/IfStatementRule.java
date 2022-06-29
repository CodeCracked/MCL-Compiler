package mcl.compiler.parser.rules.statements;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.BlockStatementNode;
import mcl.compiler.parser.nodes.statements.IfStatementNode;

public class IfStatementRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        int indent = parser.getCurrentIndent();
        GrammarRule blockRule = GrammarRules.blockStatement(indent + 1);

        // If Keyword
        Token ifKeyword = parser.getCurrentToken();
        if (!ifKeyword.isKeyword(MCLKeywords.IF)) return result.failure(new MCLSyntaxError(parser, "Expected 'if'"));
        result.registerAdvancement();
        parser.advance();

        // Check Condition Start
        if (parser.getCurrentToken().type() != TokenType.LPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
        result.registerAdvancement();
        parser.advance();

        // Condition Expression
        AbstractNode condition = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        // Check Condition End
        if (parser.getCurrentToken().type() != TokenType.RPAREN) return result.failure(new MCLSyntaxError(parser, "Expected '('"));
        result.registerAdvancement();
        parser.advance();

        // True Block
        AbstractNode trueBlock = result.register(blockRule.build(parser));
        if (result.error() != null) return result;
        if (parser.getCurrentToken().type() != TokenType.NEWLINE && !parser.getCurrentToken().isKeyword(MCLKeywords.ELSE)) return result.failure(new MCLSyntaxError(parser, "Expected 'else' or end of line"));

        if (parser.getCurrentToken().type() == TokenType.NEWLINE)
        {
            result.registerAdvancement();
            parser.advance();
        }

        AbstractNode falseBlock = null;
        // Inline Else Block
        if (parser.getCurrentToken().isKeyword(MCLKeywords.ELSE))
        {
            // Clear Else Keyword
            result.registerAdvancement();
            parser.advance();

            // Build False Block
            if (parser.getCurrentToken().isKeyword(MCLKeywords.IF)) falseBlock = result.register(GrammarRules.IF_STATEMENT.build(parser));
            else falseBlock = result.register(blockRule.build(parser));
            if (result.error() != null) return result;
        }

        // Newline Else Block
        else if (parser.getCurrentToken().type() == TokenType.INDENT && parser.peekNextToken().isKeyword(MCLKeywords.ELSE))
        {
            // Check Indent Level
            int currentIndent = parser.getCurrentToken().type() == TokenType.INDENT ? (int)parser.getCurrentToken().value() : indent;
            if (currentIndent == indent)
            {
                // Clear Indent and Else Keyword
                result.registerAdvancement();
                parser.advance();
                result.registerAdvancement();
                parser.advance();
                // Build False Block
                if (parser.getCurrentToken().isKeyword(MCLKeywords.IF)) falseBlock = result.register(GrammarRules.IF_STATEMENT.build(parser));
                else falseBlock = result.register(blockRule.build(parser));
                if (result.error() != null) return result;
            }
        }

        // Finally Block
        BlockStatementNode finallyBlock = (BlockStatementNode) result.register(GrammarRules.blockStatement(indent).build(parser));
        if (result.error() != null) return result;
        if (finallyBlock.statements.isEmpty()) finallyBlock = null;

        return result.success(new IfStatementNode(ifKeyword, condition, trueBlock, falseBlock, finallyBlock));
    }
}
