package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLWrongTypeError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.expressions.ExpressionNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
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
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = value.symbolAnalysis(compiler, source);
        if (error != null) return error;

        // Ensure type matching
        RuntimeType valueType = value.getRuntimeType(compiler);
        if (!signature.type.isAssignableFrom(valueType)) return new MCLWrongTypeError(compiler, value, signature.type, valueType);

        return null;
    }

    @Override
    public void setTranspileTarget(MCLTranspiler transpiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        this.value.setTranspileTarget(transpiler, target);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        // Print Header Comment
        MCLError error = transpiler.appendToFile(transpileTarget, file -> file.println("# VAR_DEFINITION " + signature));
        if (error != null) return error;

        // Transpile Value Node
        if (value instanceof ExpressionNode expression) error = expression.transpile(transpiler, signature.type);
        else error = value.transpile(transpiler);
        if (error != null) return error;

        // Transpile Variable Assignment
        error = transpiler.appendToFile(transpileTarget, file ->
        {
            if (signature.type == RuntimeType.INTEGER) file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s int 1 run scoreboard players get r0 {config.expressions}", signature.symbol.tableLocation));
            else if (signature.type == RuntimeType.FLOAT) file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s float 0.%s1 run scoreboard players get r0 {config.expressions}", signature.symbol.tableLocation, "0".repeat(transpiler.getCompiler().config.floatDecimalPlaces - 1)));
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
