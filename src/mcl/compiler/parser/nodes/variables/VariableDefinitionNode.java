package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.expressions.ExpressionNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class VariableDefinitionNode extends AbstractNode
{
    public final VariableSignatureNode signature;
    public final AbstractNode value;

    public VariableDefinitionNode(AbstractNode signature, AbstractNode valueNode)
    {
        super(signature.startPosition(), valueNode.endPosition());

        this.signature = (VariableSignatureNode)signature;
        this.value = valueNode;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, signature);
        signature.walk(parentChildConsumer);

        parentChildConsumer.accept(this, value);
        value.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = signature.createSymbols(compiler, source);
        if (error != null) return error;

        return value.createSymbols(compiler, source);
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        // Print Header Comment
        MCLError error = transpiler.appendToFile(target, file -> file.println("# VAR_DEFINITION " + signature));
        if (error != null) return error;

        // Transpile Value Node
        if (value instanceof ExpressionNode expression) error = expression.transpile(transpiler, target, signature.type);
        else error = value.transpile(transpiler, target);
        if (error != null) return error;

        // Transpile Variable Assignment
        error = transpiler.appendToFile(target, file ->
        {
            if (signature.type == RuntimeType.INTEGER) file.printf("execute store result storage mcl:variables CallStack[0].%s int 1 run scoreboard players get r0 mcl.expressions\n", signature.symbol.tableLocation);
            else if (signature.type == RuntimeType.FLOAT) file.printf("execute store result storage mcl:variables CallStack[0].%s float 0.%s1 run scoreboard players get r0 mcl.expressions\n", signature.symbol.tableLocation, "0".repeat(transpiler.getCompiler().config.floatDecimalPlaces - 1));
            file.println("# END VAR_DEFINITION " + signature);
            file.println();
        });

        return error;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.VOID;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("VAR_DEFINITION");

        signature.debugPrint(depth + 1);
        value.debugPrint(depth + 1);
    }
}
