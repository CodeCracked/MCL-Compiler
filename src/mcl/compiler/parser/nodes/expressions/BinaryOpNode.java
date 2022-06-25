package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.MCLKeywords;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLTranspileError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.BiConsumer;

public class BinaryOpNode extends ExpressionNode
{
    private static final Set<Token> complexOperators = Token.descriptions(TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.GREATER, TokenType.LESS, TokenType.GREATER_OR_EQUAL, TokenType.LESS_OR_EQUAL);

    public AbstractNode leftNode;
    public final Token operation;
    public AbstractNode rightNode;

    public BinaryOpNode(AbstractNode leftNode, Token operation, AbstractNode rightNode)
    {
        super(leftNode.startPosition(), rightNode.endPosition());

        this.leftNode = leftNode;
        this.operation = operation;
        this.rightNode = rightNode;
    }

    @Override
    public ExpressionNode simplify()
    {
        if (leftNode instanceof ExpressionNode expressionNode) leftNode = expressionNode.simplify();
        if (rightNode instanceof ExpressionNode expressionNode) rightNode = expressionNode.simplify();
        
        if (leftNode instanceof NumberNode left && rightNode instanceof NumberNode right)
        {
            if (left.token.value() instanceof Float || right.token.value() instanceof Float)
            {
                float leftValue = left.token.value() instanceof Float ? (Float) left.token.value() : (Integer)left.token.value();
                float rightValue = right.token.value() instanceof Float ? (Float) right.token.value() : (Integer)right.token.value();

                float result;
                switch (operation.type())
                {
                    case PLUS: result = leftValue + rightValue; break;
                    case MINUS: result = leftValue - rightValue; break;
                    case MUL: result = leftValue * rightValue; break;
                    case DIV: result = leftValue / rightValue; break;
                    case MOD: result = leftValue % rightValue; break;
                    default: return this;
                }

                return new NumberNode(new Token(TokenType.FLOAT, result, startPosition(), endPosition()));
            }
            else
            {
                int leftValue = (Integer)left.token.value();
                int rightValue = (Integer)right.token.value();

                int result;
                switch (operation.type())
                {
                    case PLUS: result = leftValue + rightValue; break;
                    case MINUS: result = leftValue - rightValue; break;
                    case MUL: result = leftValue * rightValue; break;
                    case DIV: result = leftValue / rightValue; break;
                    case MOD: result = leftValue % rightValue; break;

                    case EQUALS: result = leftValue == rightValue ? 1 : 0; break;
                    case NOT_EQUALS: result = leftValue != rightValue ? 1 : 0; break;
                    case LESS: result = leftValue < rightValue ? 1 : 0; break;
                    case GREATER: result = leftValue > rightValue ? 1 : 0; break;
                    case LESS_OR_EQUAL: result = leftValue <= rightValue ? 1 : 0; break;
                    case GREATER_OR_EQUAL: result = leftValue >= rightValue ? 1 : 0; break;

                    default: return this;
                }

                return new NumberNode(new Token(TokenType.INT, result, startPosition(), endPosition()));
            }
        }
        else return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, leftNode);
        leftNode.walk(parentChildConsumer);

        parentChildConsumer.accept(this, rightNode);
        rightNode.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = leftNode.createSymbols(compiler, source);
        if (error != null) return error;

        error = rightNode.createSymbols(compiler, source);
        return error;
    }

    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, Path target, RuntimeType targetType, int depth)
    {
        ExpressionTranspileResult leftResult = ((ExpressionNode)leftNode).castAndTranspile(transpiler, target, targetType, depth + 1);
        if (leftResult.error != null) return leftResult;

        ExpressionTranspileResult rightResult = ((ExpressionNode)rightNode).castAndTranspile(transpiler, target, targetType, leftResult.nextAvailableDepthCode);
        if (rightResult.error != null) return rightResult;

        MCLError error;

        if (operation.type() == TokenType.PLUS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} += r%s {config.expressions}", depth, rightResult.returnCode));
        });
        else if (operation.type() == TokenType.MINUS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} -= r%s {config.expressions}", depth, rightResult.returnCode));
        });
        else if (operation.type() == TokenType.MUL) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} *= r%s {config.expressions}", depth, rightResult.returnCode));
            // Divide By 1000
        });
        else if (operation.type() == TokenType.DIV) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, leftResult.returnCode));
            // Multiply by 1000
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} /= r%s {config.expressions}", depth, rightResult.returnCode));
        });
        else if (operation.type() == TokenType.MOD) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} %%= r%s {config.expressions}", depth, rightResult.returnCode));
        });
        else if (operation.isKeyword(MCLKeywords.AND)) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} matches 1.. if score r%s {config.expressions} matches 1.. run scoreboard players set r%s {config.expressions} 1", leftResult.returnCode, rightResult.returnCode, depth));
        });
        else if (operation.isKeyword(MCLKeywords.OR)) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} matches 1.. run scoreboard players set r%s {config.expressions} 1", leftResult.returnCode, depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} matches 1.. run scoreboard players set r%s {config.expressions} 1", rightResult.returnCode, depth));
        });

        else if (operation.type() == TokenType.EQUALS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} = r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });
        else if (operation.type() == TokenType.NOT_EQUALS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute unless score r%s {config.expressions} = r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });
        else if (operation.type() == TokenType.LESS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} < r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });
        else if (operation.type() == TokenType.LESS_OR_EQUAL) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} <= r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });
        else if (operation.type() == TokenType.GREATER) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} > r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });
        else if (operation.type() == TokenType.GREATER_OR_EQUAL) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", rightResult.nextAvailableDepthCode, leftResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s {config.expressions} >= r%s {config.expressions} run scoreboard players set r%s {config.expressions} 1", rightResult.nextAvailableDepthCode, rightResult.returnCode, depth));
        });

        else error = new MCLTranspileError(transpiler.getSource(), operation, "Invalid binary operation '" + operation.type() + "'");

        return new ExpressionTranspileResult(error, depth, rightResult.nextAvailableDepthCode + (operation.matches(complexOperators) ? 1 : 0));
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        RuntimeType left = leftNode.getRuntimeType(compiler);
        RuntimeType right = rightNode.getRuntimeType(compiler);
        return left.getCombinedType(right);
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println(operation);

        leftNode.debugPrint(depth + 1);
        rightNode.debugPrint(depth + 1);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s, %s)", leftNode, operation, rightNode);
    }
}
