package mcl.parser.grammar.components;

import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.util.Result;
import mcl.parser.nodes.components.QualifiedIdentifierNode;

public class QualifiedIdentifierRule implements IGrammarRule<QualifiedIdentifierNode>
{
    @Override
    public Result<QualifiedIdentifierNode> build(Parser parser)
    {
        Result<QualifiedIdentifierNode> result = new Result<>();
        
        // First Identifier
        IdentifierNode first = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Optional Second Identifier
        if (parser.getCurrentToken().type() == GrammarTokenType.COLON)
        {
            // Clear Colon
            parser.advance();
            result.registerAdvancement();
            
            // Second Identifier
            IdentifierNode second = result.register(DefaultRules.IDENTIFIER.build(parser));
            if (result.getFailure() != null) return result;
            
            return result.success(new QualifiedIdentifierNode(first, second));
        }
        else return result.success(new QualifiedIdentifierNode(first));
    }
}
