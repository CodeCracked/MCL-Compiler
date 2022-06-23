package mcl.compiler.parser.rules;

import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.GrammarRule;
import mcl.compiler.parser.GrammarRules;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.parser.ParseResult;

import java.util.Set;

public class FactorRule implements GrammarRule
{
    private final Set<Token> arithmeticOperators = Token.descriptions(TokenType.MUL, TokenType.DIV);

    @Override
    public ParseResult build(MCLParser parser)
    {
        return GrammarRules.binaryOperationRule(parser, GrammarRules.ATOM, arithmeticOperators);
    }
}
