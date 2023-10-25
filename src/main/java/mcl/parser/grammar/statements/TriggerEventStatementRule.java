package mcl.parser.grammar.statements;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.ArgumentListNode;
import compiler.core.util.Result;
import compiler.core.util.exceptions.UnexpectedTokenException;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;
import mcl.parser.nodes.components.QualifiedIdentifierNode;
import mcl.parser.nodes.statements.TriggerEventStatementNode;

public class TriggerEventStatementRule implements IGrammarRule<TriggerEventStatementNode>
{
    @Override
    public Result<TriggerEventStatementNode> build(Parser parser)
    {
        Result<TriggerEventStatementNode> result = new Result<>();
        
        // Keyword
        Token keyword = parser.getCurrentToken();
        if (keyword.type() != MCLKeyword.TRIGGER) return result.failure(UnexpectedTokenException.expected(parser, "'trigger'"));
        parser.advance();
        result.registerAdvancement();
        
        // Event
        QualifiedIdentifierNode event = result.register(MCLRules.QUALIFIED_IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Arguments
        ArgumentListNode arguments = result.register(MCLRules.ARGUMENT_LIST.build(parser));
        if (result.getFailure() != null) return result;
        
        // Semicolon
        Token semicolon = parser.getCurrentToken();
        if (semicolon.type() != GrammarTokenType.SEMICOLON) return result.failure(UnexpectedTokenException.expected(parser, "';'"));
        parser.advance();
        result.registerAdvancement();
        
        return result.success(new TriggerEventStatementNode(keyword, event, arguments, semicolon));
    }
}
