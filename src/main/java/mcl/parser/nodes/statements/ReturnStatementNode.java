package mcl.parser.nodes.statements;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.util.Result;
import compiler.core.util.annotations.OptionalChild;
import compiler.core.util.exceptions.CompilerException;
import compiler.core.util.types.DataType;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;

public class ReturnStatementNode extends AbstractNode
{
    @OptionalChild public final AbstractValueNode expression;
    
    public ReturnStatementNode(Token keyword, AbstractValueNode expression, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        this.expression = expression;
    }
    
    @Override
    protected Result<Void> validate()
    {
        Result<Void> result = new Result<>();
        
        // If parent is a BlockNode, check that it has no statements after the 'return' statement
        if (parent() instanceof BlockNode block)
        {
            // Iterate through all the block's children except the final statement
            for (int i = 0; i < block.children.size() - 1; i++)
            {
                // If the statement is a return statement
                if (block.children.get(i) instanceof ReturnStatementNode invalidReturn)
                {
                    // If the return statement is this node
                    if (invalidReturn == this)
                    {
                        // This return statement is invalid because there are trailing statements
                        AbstractNode unreachable = block.children.get(i + 1);
                        return result.failure(new CompilerException(unreachable.start(), unreachable.end(), "Unreachable statement!"));
                    }
                }
            }
        }
        
        // Validate expression type matches expected return type
        DataType expressionType = expression == null ? MCLDataTypes.VOID : expression.getValueType();
        Result<AbstractFunctionDeclarationNode> functionDeclaration = findParentNode(AbstractFunctionDeclarationNode.class, true);
        if (functionDeclaration.getFailure() == null)
        {
            DataType returnType = functionDeclaration.get().signature.returnType.value;
            if (!expressionType.canImplicitCast(returnType)) return result.failure(new CompilerException(start(), end(), "Function cannot return " + expressionType.name() + "! The function's return type is " + returnType.name() + "."));
        }
        else
        {
            Result<ListenerDeclarationNode> listenerDeclaration = findParentNode(ListenerDeclarationNode.class);
            if (listenerDeclaration.getFailure() == null)
            {
                if (expressionType != MCLDataTypes.VOID) return result.failure(new CompilerException(start(), end(), "Listeners cannot return a value! Only void returns are allowed."));
            }
            else return result.failure(new CompilerException(start(), end(), "Could not find valid function-like node for return statement!"));
        }
        
        return result.success(null);
    }
}
