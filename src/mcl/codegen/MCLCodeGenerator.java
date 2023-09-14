package mcl.codegen;

import compiler.core.codegen.CodeGenerator;
import compiler.core.parser.nodes.RootNode;
import compiler.core.parser.nodes.components.BlockNode;
import mcl.codegen.rules.*;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;

public class MCLCodeGenerator extends CodeGenerator
{
    @Override
    protected void addDefaultRules()
    {
        addRule(RootNode.class, new RootGenerator());
        addRule(MCLSourceNode.class, new MCLSourceGenerator());
        addRule(NamespaceNode.class, new NamespaceGenerator());
        addRule(ListenerDeclarationNode.class, new ListenerDeclarationGenerator());
        addRule(BlockNode.class, new BlockGenerator());
    }
}
