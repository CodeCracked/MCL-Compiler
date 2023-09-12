package mcl.codegen;

import compiler.core.codegen.CodeGenerator;
import compiler.core.parser.nodes.RootNode;
import mcl.codegen.rules.MCLSourceGenerator;
import mcl.codegen.rules.NamespaceGenerator;
import mcl.codegen.rules.RootGenerator;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;

public class MCLCodeGenerator extends CodeGenerator
{
    @Override
    protected void addDefaultRules()
    {
        addRule(RootNode.class, new RootGenerator());
        addRule(MCLSourceNode.class, new MCLSourceGenerator());
        addRule(NamespaceNode.class, new NamespaceGenerator());
    }
}
