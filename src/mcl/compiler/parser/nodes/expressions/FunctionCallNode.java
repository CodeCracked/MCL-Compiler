package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLFunctionCallError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.LocationNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class FunctionCallNode extends ExpressionNode
{
    public final LocationNode location;
    public final List<AbstractNode> arguments;

    private FunctionSymbol function;

    public FunctionCallNode(LocationNode location, List<AbstractNode> arguments, Token closingToken)
    {
        super(location.startPosition(), closingToken.endPosition());

        this.location = location;
        this.arguments = Collections.unmodifiableList(arguments);
    }

    @Override
    public ExpressionNode simplify()
    {
        return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (AbstractNode argument : arguments)
        {
            parentChildConsumer.accept(this, argument);
            argument.walk(parentChildConsumer);
        }
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }

    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error;

        // Analyze children
        for (AbstractNode argument : arguments)
        {
            error = argument.symbolAnalysis(compiler, source);
            if (error != null) return error;
        }

        // Check if function is defined
        error = location.symbolAnalysis(compiler, source, SymbolType.FUNCTION);
        if (error != null) return error;

        // Check function parameters
        function = (FunctionSymbol)location.getSymbol(compiler, SymbolType.FUNCTION);
        if (function.parameters.size() != arguments.size()) return new MCLFunctionCallError(compiler, this, function);
        for (int i = 0; i < function.parameters.size(); i++) if (!function.parameters.get(i).type.equals(arguments.get(i).getRuntimeType(compiler))) return new MCLFunctionCallError(compiler, this, function);

        return null;
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target) throws IOException
    {
        this.transpileTarget = target;
        for (AbstractNode argument : arguments) argument.setTranspileTarget(compiler, target);
    }
    @Override
    public MCLError transpile(MCLTranspiler transpiler) throws IOException
    {
        ExpressionTranspileResult result = transpileExpression(transpiler, RuntimeType.VOID, 0);
        return result.error;
    }
    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, RuntimeType targetType, int depth) throws IOException
    {
        ExpressionTranspileResult result = new ExpressionTranspileResult(null, depth, depth);

        // Print Header Comment
        result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# FUNC_CALL " + location.toString()));
        if (result.error != null) return result;

        // Push Call Stack
        result.error = transpiler.pushStacks(transpileTarget);
        if (result.error != null) return result;

        // Backup Expressions Objective
        if (depth > 0)
        {
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# BACKUP_EXPRESSION_STACK"));
            if (result.error != null) return result;
            for (int i = 0; i < depth; i++)
            {
                int register = i;
                result.error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("execute store result storage {config.variables} ExpressionStack[0].r%1$s int 1 run scoreboard players get r%1$s {config.expressions}", register)));
                if (result.error != null) return result;
            }
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# END BACKUP_EXPRESSION_STACK"));
            if (result.error != null) return result;
        }

        // Assign Arguments
        int currentDepth = depth + 1;
        for (int i = 0; i < arguments.size(); i++)
        {
            AbstractNode value = arguments.get(i);
            VariableSymbol argument = function.parameters.get(i);

            // Print Header Comment
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# ARG_ASSIGN " + argument.identifier.value() + "(" + argument.type + ")"));
            if (result.error != null) return result;

            // Transpile Value Expression
            if (value instanceof ExpressionNode expression)
            {
                ExpressionTranspileResult argumentResult = expression.castAndTranspile(transpiler, argument.type, currentDepth);
                currentDepth = argumentResult.nextAvailableDepthCode;

                // Write Assignment
                result.error = transpiler.assignVariable(transpileTarget, argument, argumentResult.returnCode);
                if (result.error != null) return result;
            }

            // Print Footer Comment
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# END ARG_ASSIGN " + argument.identifier.value() + "(" + argument.type + ")"));
            if (result.error != null) return result;
        }

        // Call Function Main File
        result.error = transpiler.runFunctionFile(transpileTarget, function.mainFunctionFile);
        if (result.error != null) return result;

        // Copy Return to Register
        if (!function.returnType.equals(RuntimeType.VOID))
        {
            result.error = transpiler.accessVariable(transpileTarget, "return", function.returnType, depth);
            if (result.error != null) return result;
        }

        // Restore Expressions Objective
        if (depth > 0)
        {
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# RESTORE_EXPRESSION_STACK"));
            if (result.error != null) return result;
            for (int i = 0; i < depth; i++)
            {
                int register = i;
                result.error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("execute store result score r%1$s {config.expressions} run data get storage {config.variables} ExpressionStack[0].r%1$s 1", register)));
                if (result.error != null) return result;
            }
            result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# END RESTORE_EXPRESSION_STACK"));
        }

        // Pop Call Stack
        result.error = transpiler.popStacks(transpileTarget);
        if (result.error != null) return result;

        // Print Footer Comment
        result.error = transpiler.appendToFile(transpileTarget, file -> file.println("# END FUNC_CALL " + location.toString()));
        return result;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        function = (FunctionSymbol)location.getSymbol(compiler, SymbolType.FUNCTION);
        return function.returnType;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_CALL");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(location);

        for (AbstractNode argument : arguments) argument.debugPrint(depth + 1);
    }
}
