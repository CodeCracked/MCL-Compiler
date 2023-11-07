package mcl.parser.symbols;

import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.parser.symbols.types.AbstractFunctionSymbol;

public class FunctionSymbol<T extends AbstractFunctionDeclarationNode<? extends FunctionSymbol<?>>> extends AbstractFunctionSymbol
{
    public final String namespace;
    
    public FunctionSymbol(T definition, String namespace, String name)
    {
        super(definition, name);
        this.namespace = namespace;
    }
}
