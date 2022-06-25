package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLFunctionCallError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class FunctionCallNode extends ExpressionNode
{
    public final Token identifier;
    public final List<AbstractNode> arguments;

    private FunctionSymbol function;

    public FunctionCallNode(Token identifier, List<AbstractNode> arguments, Token closingToken)
    {
        super(identifier.startPosition(), closingToken.endPosition());

        this.identifier = identifier;
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
        error = compiler.getSymbolTable().checkSymbolDefinition(identifier, SymbolType.FUNCTION);
        if (error != null) return error;

        // Check function parameters
        function = (FunctionSymbol)compiler.getSymbolTable().getSymbol((String)identifier.value(), SymbolType.FUNCTION);
        if (function.parameters.size() != arguments.size()) return new MCLFunctionCallError(compiler, this, function);
        for (int i = 0; i < function.parameters.size(); i++) if (!function.parameters.get(i).equals(arguments.get(i).getRuntimeType(compiler))) return new MCLFunctionCallError(compiler, this, function);

        return null;
    }

    @Override
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        ExpressionTranspileResult result = transpileExpression(transpiler, target, RuntimeType.VOID, 0);
        return result.error;
    }

    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, Path target, RuntimeType targetType, int depth)
    {
        ExpressionTranspileResult result = new ExpressionTranspileResult(null, depth, depth);

        // Print Header Comment
        MCLError mclError = transpiler.appendToFile(target, file -> file.println("# FUNC_CALL " + identifier.value()));
        if (mclError != null) return new ExpressionTranspileResult(mclError, depth, depth);

        // Print Footer Comment
        mclError = transpiler.appendToFile(target, file -> file.println("# END FUNC_CALL " + identifier.value()));
        if (mclError != null) return result;

        return result;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        function = (FunctionSymbol)compiler.getSymbolTable().getSymbol((String)identifier.value(), SymbolType.FUNCTION);
        return function.returnType;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_CALL");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        for (AbstractNode argument : arguments) argument.debugPrint(depth + 1);
    }
}
