package mcl.parser.grammar.statements;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.MathTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Result;
import mcl.parser.MCLRules;
import mcl.parser.nodes.declarations.MCLVariableDeclarationNode;

public class VariableDeclarationStatementRule implements IGrammarRule<MCLVariableDeclarationNode>
{
    @Override
    public Result<MCLVariableDeclarationNode> build(Parser parser)
    {
        Result<MCLVariableDeclarationNode> result = new Result<>();
        
        // Data Type
        DataTypeNode type = result.register(DefaultRules.DATA_TYPE.build(parser));
        if (result.getFailure() != null) return result;
        
        // Identifier
        IdentifierNode identifier = result.register(DefaultRules.IDENTIFIER.build(parser));
        if (result.getFailure() != null) return result;
        
        // Optional Initializer
        AbstractValueNode initialValue = null;
        if (parser.getCurrentToken().type() == MathTokenType.ASSIGN)
        {
            // Clear Assignment Token
            result.registerAdvancement();
            parser.advance();
    
            // Initial Value
            initialValue = result.register(MCLRules.EXPRESSION.build(parser));
            if (result.getFailure() != null) return result;
        }
        
        // Semicolon
        Token semicolon = result.register(tokenType(parser, GrammarTokenType.SEMICOLON, "';'"));
        if (result.getFailure() != null) return result;
        
        return result.success(new MCLVariableDeclarationNode(type.start(), semicolon.end(), type, identifier, initialValue));
    }
}
