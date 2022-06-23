package mcl.compiler.parser.rules.variables;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.variables.VariableDefinitionNode;

public class VariableDefinitionRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        AbstractNode signature = result.register(GrammarRules.VARIABLE_SIGNATURE.build(parser));
        if (result.error() != null) return result;

        if (parser.getCurrentToken().type() != TokenType.ASSIGN) return result.failure(new MCLSyntaxError(parser, "Expected '='"));
        result.registerAdvancement();
        parser.advance();

        AbstractNode value = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        return result.success(new VariableDefinitionNode(signature, value));
    }
}
