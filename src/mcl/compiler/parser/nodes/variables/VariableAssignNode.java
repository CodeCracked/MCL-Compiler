package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.exceptions.MCLIllegalOperationError;
import mcl.compiler.exceptions.MCLWrongTypeError;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.expressions.ExpressionNode;
import mcl.compiler.source.MCLSourceCollection;
import mcl.compiler.transpiler.MCLTranspiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class VariableAssignNode extends AbstractNode
{
    public final Token identifier;
    public final Token operation;
    public final AbstractNode value;

    public VariableAssignNode(Token identifier, Token operation, AbstractNode valueNode)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
        this.operation = operation;
        this.value = valueNode;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        parentChildConsumer.accept(this, value);
        value.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = compiler.getSymbolTable().checkSymbolDefinition(identifier, SymbolType.VARIABLE);
        if (error != null) return error;
        return value.createSymbols(compiler, source);
    }
    @Override
    public MCLError symbolAnalysis(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = value.symbolAnalysis(compiler, source);
        if (error != null) return error;

        VariableSymbol symbol = (VariableSymbol)compiler.getSymbolTable().getSymbol((String)identifier.value(), SymbolType.VARIABLE);

        // Ensure type matching
        RuntimeType valueType = value.getRuntimeType(compiler);
        if (!symbol.type.isAssignableFrom(valueType)) return new MCLWrongTypeError(compiler, value, symbol.type, valueType);

        // No float modulus
        if (operation.type() == TokenType.ASSIGN_MOD && (symbol.type.equals(RuntimeType.FLOAT) || value.getRuntimeType(compiler).equals(RuntimeType.FLOAT)))
        {
            return new MCLIllegalOperationError(compiler, this, "Cannot perform modulo of float numbers");
        }

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
        VariableSymbol symbol = (VariableSymbol) transpiler.getCompiler().getSymbolTable().getSymbol((String)identifier.value(), SymbolType.VARIABLE);

        // Print Header Comment
        MCLError error = transpiler.appendToFile(transpileTarget, file -> file.println("# VAR_ASSIGN " + identifier.value()));
        if (error != null) return error;

        // Transpile Value Node
        if (value instanceof ExpressionNode expression) error = expression.transpile(transpiler, symbol.type);
        else error = value.transpile(transpiler);
        if (error != null) return error;

        // Transpile Operations
        if (operation.type() != TokenType.ASSIGN) error = transpiler.accessVariable(transpileTarget, symbol, 1);
        if (error != null) return error;

        if (operation.type() == TokenType.ASSIGN_PLUS) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} += r0 {config.expressions}")));
        else if (operation.type() == TokenType.ASSIGN_MINUS) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} -= r0 {config.expressions}")));
        else if (operation.type() == TokenType.ASSIGN_MUL) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} *= r0 {config.expressions}")));
        else if (operation.type() == TokenType.ASSIGN_DIV) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} /= r0 {config.expressions}")));
        else if (operation.type() == TokenType.ASSIGN_MOD) error = transpiler.appendToFile(transpileTarget, file -> file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} %= r0 {config.expressions}")));
        if (error != null) return error;

        // Transpile Assignment
        int register = operation.type() == TokenType.ASSIGN ? 0 : 1;
        error = transpiler.assignVariable(transpileTarget, symbol, register);
        if (error != null) return error;

        // Print Footer Comment
        error = transpiler.appendToFile(transpileTarget, file ->
        {
            file.println("# END VAR_ASSIGN " + identifier.value());
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
        System.out.println("VAR_ASSIGN");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        System.out.print("  ".repeat(depth + 1));
        System.out.println(operation);

        value.debugPrint(depth + 1);
    }
}
