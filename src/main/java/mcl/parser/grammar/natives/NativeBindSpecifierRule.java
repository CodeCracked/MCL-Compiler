package mcl.parser.grammar.natives;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.lexer.types.LiteralTokenType;
import compiler.core.parser.DefaultRules;
import compiler.core.parser.IGrammarRule;
import compiler.core.parser.Parser;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.Result;
import mcl.lexer.MCLKeyword;
import mcl.parser.nodes.natives.NativeBindSpecifierNode;

import java.util.ArrayList;
import java.util.List;

public class NativeBindSpecifierRule implements IGrammarRule<NativeBindSpecifierNode>
{
    @Override
    public Result<NativeBindSpecifierNode> build(Parser parser)
    {
        Result<NativeBindSpecifierNode> result = new Result<>();
        
        // Return or Parameter Identifier
        IdentifierNode parameter;
        if (parser.getCurrentToken().type() == MCLKeyword.RETURN)
        {
            parameter = new IdentifierNode(parser.getCurrentToken());
            parser.advance();
            result.registerAdvancement();
        }
        else
        {
            parameter = result.register(DefaultRules.IDENTIFIER.build(parser));
            if (result.getFailure() != null) return result;
        }
        
        // Colon
        result.register(tokenType(parser, GrammarTokenType.COLON, "':'"));
        if (result.getFailure() != null) return result;
        
        // Bind Type
        IdentifierNode bindType = result.register(DefaultRules.IDENTIFIER.build(parser));
        
        // Optional Arguments
        List<String> arguments = new ArrayList<>();
        SourcePosition end = bindType.end();
        if (parser.getCurrentToken().type() == GrammarTokenType.LPAREN)
        {
            // Opening Parenthesis
            result.register(tokenType(parser, GrammarTokenType.LPAREN, "'('"));
            if (result.getFailure() != null) return result;
            
            // Arguments
            if (parser.getCurrentToken().type() != GrammarTokenType.RPAREN)
            {
                while (true)
                {
                    // Argument
                    Token argument = result.register(tokenType(parser, LiteralTokenType.STRING, "string literal"));
                    if (result.getFailure() != null) return result;
                    arguments.add(argument.contents().toString());
                    
                    // Check for Comma
                    if (parser.getCurrentToken().type() == GrammarTokenType.COMMA)
                    {
                        parser.advance();
                        result.registerAdvancement();
                    }
                    else break;
                }
            }
            
            // Closing Parenthesis
            Token closingParenthesis = result.register(tokenType(parser, GrammarTokenType.RPAREN, "')'"));
            if (result.getFailure() != null) return result;
            end = closingParenthesis.end();
        }
        
        return result.success(new NativeBindSpecifierNode(parameter, bindType, arguments, end));
    }
}
