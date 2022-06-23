package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class VarAssignNode extends AbstractNode
{
    public final Token identifier;
    public final Token operation;
    public final AbstractNode value;

    public VarAssignNode(Token identifier, Token operation, AbstractNode valueNode)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
        this.operation = operation;
        this.value = valueNode;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = compiler.getSymbolTable().checkSymbolDefinition(identifier, SymbolType.VARIABLE);
        if (error != null) return error;
        return value.createSymbols(compiler, source);
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
