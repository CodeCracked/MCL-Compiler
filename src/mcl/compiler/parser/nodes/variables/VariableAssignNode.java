package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
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
            file.printf("execute store result storage mcl:variables CallStack[0].%s %s %s run scoreboard players get r0 mcl.expressions\n", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown);
        });
        else if (operation.type() == TokenType.ASSIGN_PLUS) error = transpiler.appendToFile(target, file ->
        {
            file.printf("execute store result score r1 mcl.expressions run data get storage mcl:variables CallStack[0].%s %s\n", symbol.tableLocation, scaleUp);
            file.printf("scoreboard players operation r1 mcl.expressions += r0 mcl.expressions\n");
            file.printf("execute store result storage mcl:variables CallStack[0].%s %s %s run scoreboard players get r1 mcl.expressions\n", symbol.tableLocation, symbol.type.getMinecraftName(), scaleDown);
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
