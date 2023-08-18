package compiler.core.parser;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.DataType;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.util.IO;
import compiler.core.util.Result;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiFunction;

@SuppressWarnings("UnusedReturnValue")
public class Parser
{
    private final IGrammarRule<?> rootRule;
    private final Stack<Integer> revertStack;
    private final BiFunction<Parser, Token, Integer> scopeDepthFunction;
    private final Map<Enum<?>, DataType> dataTypes;
    
    private List<Token> tokens;
    private int tokenIndex;
    private Token currentToken;
    private Token nextToken;
    private int currentScopeDepth;
    
    public Parser(IGrammarRule<?> rootRule, BiFunction<Parser, Token, Integer> scopeDepthFunction, Class<?> dataTypes)
    {
        this.rootRule = rootRule;
        this.revertStack = new Stack<>();
        this.scopeDepthFunction = scopeDepthFunction;
        this.dataTypes = new HashMap<>();
        
        // Load the data types from the provided class
        try
        {
            for (Field field : dataTypes.getDeclaredFields())
            {
                if (Modifier.isStatic(field.getModifiers()) && DataType.class.isAssignableFrom(field.getType()))
                {
                    field.setAccessible(true);
                    DataType dataType = (DataType) field.get(null);
                    this.dataTypes.put(dataType.keyword(), dataType);
                }
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }
    
    public static Parser bracedScope(IGrammarRule<?> rootRule, Class<?> dataTypes)
    {
        return new Parser(rootRule, (parser, token) ->
        {
            if (token.type() == GrammarTokenType.LBRACE) return parser.currentScopeDepth + 1;
            else if (token.type() == GrammarTokenType.RBRACE) return parser.currentScopeDepth - 1;
            else return parser.currentScopeDepth;
        }, dataTypes);
    }
    
    //region Parse Methods
    public Result<? extends AbstractNode> parse(List<Token> tokens)
    {
        // Initialize Parser State
        this.tokens = tokens;
        this.tokenIndex = -1;
        this.currentScopeDepth = 0;
        this.revertStack.clear();
        advance();
        
        // Try to parse the AST
        Result<? extends AbstractNode> result = rootRule.build(this);
        if (result.getFailure() != null) return result;
        result.get().decorate();
        
        // Return Result
        return result;
    }
    //endregion
    //region Position Handling
    public void markPosition() { revertStack.push(tokenIndex); }
    public void unmarkPosition()
    {
        if (revertStack.size() == 0)
        {
            String error = "Trying to unmark position of parser when no positions have been marked!";
            IO.Errors.println(error);
            throw new IllegalStateException(error);
        }
        else revertStack.pop();
    }
    public void revertPosition()
    {
        if (revertStack.size() == 0)
        {
            String error = "Trying to revert position of parser when no positions have been marked!";
            IO.Errors.println(error);
            throw new IllegalStateException(error);
        }
        else
        {
            this.tokenIndex = revertStack.pop() - 1;
            advance();
        }
    }
    public boolean advance()
    {
        this.tokenIndex++;
        this.currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : null;
        this.nextToken = (tokenIndex + 1) < tokens.size() ? tokens.get(tokenIndex + 1) : null;
        
        if (this.currentToken != null)
        {
            this.currentScopeDepth = scopeDepthFunction.apply(this, currentToken);
            return true;
        }
        else return false;
    }
    //endregion
    //region Getters
    public Token getCurrentToken() { return currentToken; }
    public Token peekNextToken() { return nextToken; }
    public int getTokenIndex() { return tokenIndex; }
    public int getCurrentScopeDepth() { return currentScopeDepth; }
    //endregion
    //region Public Helpers
    public boolean isDataType(Token token) { return dataTypes.containsKey(token.type()); }
    public DataType getDataType(Token token) { return dataTypes.get(token.type()); }
    //endregion
}
