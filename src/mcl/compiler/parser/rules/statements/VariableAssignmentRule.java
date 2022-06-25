package mcl.compiler.parser.rules.statements;

import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.variables.VariableAssignNode;

import java.util.Set;

public class VariableAssignmentRule implements GrammarRule
{
    private final Set<Token> assignOperations = Token.descriptions(TokenType.ASSIGN, TokenType.ASSIGN_PLUS, TokenType.ASSIGN_MINUS, TokenType.ASSIGN_MUL, TokenType.ASSIGN_DIV, TokenType.ASSIGN_MOD);

    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        Token identifier = parser.getCurrentToken();
        result.registerAdvancement();
        parser.advance();

        Token operation = parser.getCurrentToken();
        if (!operation.matches(assignOperations)) return result.failure(new MCLSyntaxError(parser, "Expected assignment operator"));
        result.registerAdvancement();
        parser.advance();

        AbstractNode value = result.register(GrammarRules.EXPRESSION.build(parser));
        if (result.error() != null) return result;

        return result.success(new VariableAssignNode(identifier, operation, value));
    }
}
