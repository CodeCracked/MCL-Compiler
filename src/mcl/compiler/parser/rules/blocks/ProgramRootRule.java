package mcl.compiler.parser.rules.blocks;

import mcl.compiler.lexer.TokenType;
import mcl.compiler.parser.*;
import mcl.compiler.parser.nodes.blocks.ProgramRootNode;

import java.util.ArrayList;
import java.util.List;

public class ProgramRootRule implements GrammarRule
{
    @Override
    public ParseResult build(MCLParser parser)
    {
        ParseResult result = new ParseResult();
        List<AbstractNode> namespaces = new ArrayList<>();

        while (parser.getCurrentToken().matches(TokenType.KEYWORD, "namespace"))
        {
            AbstractNode namespace = result.register(GrammarRules.NAMESPACE.build(parser));
            if (result.error() != null) return result;
            namespaces.add(namespace);
        }

        return result.success(new ProgramRootNode(namespaces));
    }
}
