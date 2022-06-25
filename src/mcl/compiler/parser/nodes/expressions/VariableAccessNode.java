package mcl.compiler.parser.nodes.expressions;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public class VariableAccessNode extends ExpressionNode
{
    private final Token identifier;

    public VariableAccessNode(Token identifier)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
    }

    @Override
    public ExpressionNode simplify()
    {
        return this;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) { }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().checkSymbolDefinition(identifier, SymbolType.VARIABLE);
    }

    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        return null;
    }

    @Override
    public void setTranspileTarget(MCLCompiler compiler, Path target)
    {
        this.transpileTarget = target;
    }
    @Override
    protected ExpressionTranspileResult transpileExpression(MCLTranspiler transpiler, RuntimeType targetType, int depth)
    {
        VariableSymbol symbol = (VariableSymbol)transpiler.getCompiler().getSymbolTable().getSymbol((String)identifier.value(), SymbolType.VARIABLE);
        MCLError error;

        error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("execute store result score r%s {config.expressions} run data get storage {config.variables} CallStack[0].%s %s",
                depth, symbol.tableLocation, symbol.type.scaleUp(transpiler.getCompiler().config))));

        return new ExpressionTranspileResult(error, depth, depth + 1);
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        Symbol symbol = compiler.getSymbolTable().getSymbol((String)identifier.value(), SymbolType.VARIABLE);
        if (symbol instanceof VariableSymbol variable) return variable.type;
        else return RuntimeType.UNDEFINED;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("VARIABLE:" + identifier.value());
    }
}
