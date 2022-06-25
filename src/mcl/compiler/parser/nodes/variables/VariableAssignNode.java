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
    public MCLError transpile(MCLTranspiler transpiler, Path target)
    {
        VariableSymbol symbol = (VariableSymbol) transpiler.getCompiler().getSymbolTable().getSymbol((String)identifier.value(), SymbolType.VARIABLE);

        // Print Header Comment
        MCLError error = transpiler.appendToFile(target, file -> file.println("# VAR_ASSIGN " + identifier.value()));
        if (error != null) return error;

        // Transpile Value Node
        if (value instanceof ExpressionNode expression) error = expression.transpile(transpiler, target, symbol.type);
        else error = value.transpile(transpiler, target);
        if (error != null) return error;

        // Calculate Scaling Strings
        String scaleDown = symbol.type.scaleDown(transpiler.getCompiler().config);
        String scaleUp = symbol.type.scaleUp(transpiler.getCompiler().config);

        // Transpile Variable Assignment
        if (operation.type() == TokenType.ASSIGN) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r0 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        else if (operation.type() == TokenType.ASSIGN_PLUS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result score r1 {config.expressions} run data get storage {config.variables} CallStack[0].%s %s", symbol.tableLocation, scaleUp));
            file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} += r0 {config.expressions}"));
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r1 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        else if (operation.type() == TokenType.ASSIGN_MINUS) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result score r1 {config.expressions} run data get storage {config.variables} CallStack[0].%s %s", symbol.tableLocation, scaleUp));
            file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} -= r0 {config.expressions}"));
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r1 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        else if (operation.type() == TokenType.ASSIGN_MUL) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result score r1 {config.expressions} run data get storage {config.variables} CallStack[0].%s %s", symbol.tableLocation, scaleUp));
            file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} *= r0 {config.expressions}"));
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r1 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        else if (operation.type() == TokenType.ASSIGN_DIV) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result score r1 {config.expressions} run data get storage {config.variables} CallStack[0].%s %s", symbol.tableLocation, scaleUp));
            file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} /= r0 {config.expressions}"));
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r1 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        else if (operation.type() == TokenType.ASSIGN_MOD) error = transpiler.appendToFile(target, file ->
        {
            file.println(transpiler.applyConfig("execute store result score r1 {config.expressions} run data get storage {config.variables} CallStack[0].%s %s", symbol.tableLocation, scaleUp));
            file.println(transpiler.applyConfig("scoreboard players operation r1 {config.expressions} %= r0 {config.expressions}"));
            file.println(transpiler.applyConfig("execute store result storage {config.variables} CallStack[0].%s %s %s run scoreboard players get r1 {config.expressions}", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown));
        });
        if (error != null) return error;

        // Print Footer Comment
        error = transpiler.appendToFile(target, file ->
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
