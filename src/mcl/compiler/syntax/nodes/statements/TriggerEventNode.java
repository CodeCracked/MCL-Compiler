package mcl.compiler.syntax.nodes.statements;

import mcl.compiler.exceptions.MCLSemanticException;
import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;
import mcl.compiler.syntax.nodes.AbstractSyntaxNode;

public class TriggerEventNode extends FunctionCallNode
{
    public TriggerEventNode(AbstractSyntaxNode parent, SyntaxAnalyzer syntax) throws MCLSyntaxException, MCLSemanticException
    {
        super(parent, syntax);
    }
}
