package compiler.core.parser;

import compiler.core.lexer.Token;
import compiler.core.lexer.types.GrammarTokenType;
import compiler.core.parser.nodes.RootNode;
import compiler.core.source.SourcePosition;
import compiler.core.util.IO;
import compiler.core.util.Result;
import compiler.core.util.types.DataTypeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

@SuppressWarnings("UnusedReturnValue")
public class Parser
{
    private final DataTypeList dataTypes;
    private final IGrammarRule<?> sourceRule;
    private final Stack<Integer> revertStack;
    private final BiFunction<Parser, Token, Integer> scopeDepthFunction;
    
    private List<Token> tokens;
    private int tokenIndex;
    private Token currentToken;
    private Token nextToken;
    private int currentScopeDepth;
    
    public Parser(DataTypeList dataTypes, IGrammarRule<?> sourceRule, BiFunction<Parser, Token, Integer> scopeDepthFunction)
    {
        this.dataTypes = dataTypes;
        this.sourceRule = sourceRule;
        this.revertStack = new Stack<>();
        this.scopeDepthFunction = scopeDepthFunction;
    }
    
    public static Parser bracedScope(DataTypeList dataTypes, IGrammarRule<?> sourceRule)
    {
        return new Parser(dataTypes, sourceRule, (parser, token) ->
        {
            if (token.type() == GrammarTokenType.LBRACE) return parser.currentScopeDepth + 1;
            else if (token.type() == GrammarTokenType.RBRACE) return parser.currentScopeDepth - 1;
            else return parser.currentScopeDepth;
        });
    }
    
    //region Parse Methods
    public Result<RootNode> parse(List<Token>[] tokens)
    {
        Result<RootNode> result = new Result<>();
        
        List<AbstractNode> fileNodes = new ArrayList<>();
        int failedSourceCount = 0;
        for (List<Token> file : tokens)
        {
            Result<? extends AbstractNode> fileAST = parseFile(file);
            if (fileAST.getFailure() == null) fileNodes.add(fileAST.get());
            else
            {
                result.appendIssues(fileAST);
                failedSourceCount++;
            }
        }
    
        SourcePosition start = tokens[0].get(0).start();
        SourcePosition end = tokens[tokens.length - 1].get(tokens[tokens.length - 1].size() - 1).end();
        return result.success(new RootNode(start, end, fileNodes, failedSourceCount));
    }
    private Result<? extends AbstractNode> parseFile(List<Token> tokens)
    {
        // Initialize Parser State
        this.tokens = tokens;
        this.tokenIndex = -1;
        this.currentScopeDepth = 0;
        this.revertStack.clear();
        advance();
        
        // Try to parse the AST
        Result<? extends AbstractNode> result = sourceRule.build(this);
        if (result.getFailure() != null) return result;
        else result.register(result.get().decorate());
        
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
    public DataTypeList getDataTypes() { return dataTypes; }
    //endregion
}
