package mcl.compiler.parser.rules.statements;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.statements.ReturnNode;

public class ReturnRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        Token keyword = parser.getCurrentToken();
        if (!keyword.isKeyword(MCLKeywords.RETURN)) return result.failure(new MCLSyntaxError(parser, "Expected 'return'"));
        result.registerAdvancement();
        parser.advance();

        AbstractNode value = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        return result.success(new ReturnNode(keyword, value));
    }
}