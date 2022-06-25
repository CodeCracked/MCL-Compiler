package mcl.compiler.parser.rules.statements;

import mcl.compiler.MCLKeywords;
import mcl.compiler.exceptions.MCLSyntaxError;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;

public class StatementRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();

        // Variable Definition
        if (parser.getCurrentToken().isKeyword(MCLKeywords.VARIABLE_TYPES))
        {
            AbstractNode definition = result.register(GrammarRules.VARIABLE_DEFINITION.build(parser));
            if (result.error() != null) return result;
            return result.success(definition);
        }

        // Function Calls or Variable Assignments
        else if (parser.getCurrentToken().type() == TokenType.IDENTIFIER)
        {
            // Function Calls
            if (parser.peekNextToken().type() == TokenType.LPAREN)
            {
                AbstractNode functionCall = result.register(GrammarRules.FUNCTION_CALL.build(parser));
                if (result.error() != null) return result;
                return result.success(functionCall);
            }

            // Variable Assignments
            else
            {
                AbstractNode assignment = result.register(GrammarRules.VARIABLE_ASSIGNMENT.build(parser));
                if (result.error() != null) return result;
                return result.success(assignment);
            }
        }

        // Function Definitions
        else if (parser.getCurrentToken().isKeyword(MCLKeywords.FUNC))
        {
            AbstractNode function = result.register(GrammarRules.FUNCTION.build(parser));
            if (result.error() != null) return result;
            return result.success(function);
        }

        return result.failure(new MCLSyntaxError(parser, "Not a statement"));
    }
}
