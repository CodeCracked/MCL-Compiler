package mcl.compiler.syntax.nodes.blocks;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.syntax.SyntaxAnalyzer;

public class ListenerDefinitionNode extends FunctionDefinitionNode
{
    public ListenerDefinitionNode(NamespaceNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax);
    }
}
