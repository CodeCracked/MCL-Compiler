package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.Symbol;
import mcl.compiler.analyzer.SymbolType;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class VarAccessNode extends AbstractNode
{
    private final Token identifier;

    public VarAccessNode(Token identifier)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.identifier = identifier;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        return compiler.getSymbolTable().checkSymbolDefinition(identifier, SymbolType.VARIABLE);
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
