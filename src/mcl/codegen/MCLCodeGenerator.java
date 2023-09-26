package mcl.codegen;

import compiler.core.codegen.CodeGenerator;
import compiler.core.parser.nodes.RootNode;
import compiler.core.parser.nodes.components.BlockNode;
import mcl.codegen.rules.*;
import mcl.codegen.rules.symbols.EventGenerator;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;
import mcl.parser.symbols.EventSymbol;

public class MCLCodeGenerator extends CodeGenerator
{
    @Override
    protected void addDefaultRules()
    {
        addNodeRule(RootNode.class, new RootGenerator());
        addNodeRule(MCLSourceNode.class, new MCLSourceGenerator());
        addNodeRule(NamespaceNode.class, new NamespaceGenerator());
        addNodeRule(ListenerDeclarationNode.class, new ListenerDeclarationGenerator());
        addNodeRule(BlockNode.class, new BlockGenerator());
        
        addSymbolRule(EventSymbol.class, new EventGenerator());
    }
}
