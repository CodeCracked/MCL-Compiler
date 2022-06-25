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

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class UnaryOpNode extends ExpressionNode
{
    public final Token operation;
    public AbstractNode node;

    public UnaryOpNode(Token operation, AbstractNode node)
    {
        super(operation.startPosition(), node.endPosition());

        this.operation = operation;
        this.node = node;
    }

    @Override
    public ExpressionNode simplify()
    {
        if (node instanceof ExpressionNode expressionNode) node = expressionNode.simplify();

        if (node instanceof NumberNode numberNode)
        {
            if (numberNode.token.value() instanceof Integer number)
            {
                if (operation.type() == TokenType.PLUS) return new NumberNode(new Token(TokenType.INT, Math.abs(number), startPosition(), endPosition()));
                else if (operation.type() == TokenType.MINUS) return new NumberNode(new Token(TokenType.INT, -number, startPosition(), endPosition()));
                else if (operation.isKeyword(MCLKeywords.NOT)) return new NumberNode(new Token(TokenType.INT, number > 0 ? 0 : 1, startPosition(), endPosition()));
                else return this;
            }
            else if (numberNode.token.value() instanceof Float number)
            {
                if (operation.type() == TokenType.PLUS) return new NumberNode(new Token(TokenType.FLOAT, Math.abs(number), startPosition(), endPosition()));
                else if (operation.type() == TokenType.MINUS) return new NumberNode(new Token(TokenType.FLOAT, -number, startPosition(), endPosition()));
                else if (operation.isKeyword(MCLKeywords.NOT)) return new NumberNode(new Token(TokenType.INT, number > 0 ? 0 : 1, startPosition(), endPosition()));
                else return this;
            }
            else return this;
        }
        else return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, node);
        node.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return node.createSymbols(compiler, source);
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        return node.symbolAnalysis(compiler, source);
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        node.setTranspileTarget(compiler, target);
    }
    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, RuntimeType targetType, int depth) throws IOException
    {
        ExpressionTranspileResult nodeResult = ((ExpressionNode)node).castAndTranspile(transpiler, targetType, depth + 1);
        if (nodeResult.error != null) return nodeResult;

        MCLError error;

        if (operation.type() == TokenType.MINUS) error = transpiler.appendToFile(transpileTarget, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, nodeResult.returnCode));
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} *= -1 {config.constants}", depth));
        });
        else if (operation.type() == TokenType.PLUS) error = transpiler.appendToFile(transpileTarget, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players operation r%s {config.expressions} = r%s {config.expressions}", depth, nodeResult.returnCode));
            file.println(transpiler.applyConfig("execute if score r%1$s matches ..0 run scoreboard players operation r%1$s {config.expressions} *= -1 {config.constants}", depth));
        });
        else if (operation.isKeyword(MCLKeywords.NOT)) error = transpiler.appendToFile(transpileTarget, file ->
        {
            file.println(transpiler.applyConfig("scoreboard players set r%s {config.expressions} 0", depth));
            file.println(transpiler.applyConfig("execute if score r%s matches ..0 run scoreboard players set r%s {config.expressions} 1", nodeResult.returnCode, depth));
        });
        else error = new MCLTranspileError(transpiler.getSource(), operation, "Invalid unary operation '" + operation.type() + "'");

        return new ExpressionTranspileResult(error, depth, nodeResult.nextAvailableDepthCode);
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return node.getRuntimeType(compiler);
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println(operation);

        node.debugPrint(depth + 1);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s)", operation, node);
    }
}
