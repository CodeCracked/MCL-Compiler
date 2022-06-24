package mcl.compiler.parser.nodes.variables;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.function.BiConsumer;

public class VariableSignatureNode extends AbstractNode
{
    public final RuntimeType type;
    public final Token identifier;

    public VariableSignatureNode(Token type, Token identifier)
    {
        super(type.startPosition(), identifier.endPosition());

        this.type = RuntimeType.parse((String)type.value());
        this.identifier = identifier;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer) { }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().addSymbol(new VariableSymbol(identifier, type));
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return type;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("VAR_SIGNATURE" + ":" + identifier.value() + "(" + type.toString() + ")");
    }
}
