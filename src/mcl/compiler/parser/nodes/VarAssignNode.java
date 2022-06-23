package mcl.compiler.parser.nodes;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.symbols.VariableSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class VarAssignNode extends AbstractNode
{
    public final Token keyword;
    public final Token identifier;
    public final AbstractNode value;

    public VarAssignNode(Token keyword, Token identifier, AbstractNode valueNode)
    {
        super(identifier.startPosition(), identifier.endPosition());

        this.keyword = keyword;
        this.identifier = identifier;
        this.value = valueNode;
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = compiler.getSymbolTable().addSymbol(new VariableSymbol(identifier, keyword));
        if (error != null) return error;

        return value.createSymbols(compiler, source);
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("ASSIGN:" + keyword.value());

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        value.debugPrint(depth + 1);
    }
}
