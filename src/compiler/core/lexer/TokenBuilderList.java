package compiler.core.lexer;

import compiler.core.source.SourcePosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenBuilderList extends AbstractTokenBuilder
{
    private final List<AbstractTokenBuilder> builders;
    
    public TokenBuilderList(AbstractTokenBuilder... builders)
    {
        this.builders = new ArrayList<>(builders.length);
        Collections.addAll(this.builders, builders);
    }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        for (AbstractTokenBuilder builder : builders)
        {
            position.markPosition();
            Token token = builder.tryBuild(lexer, position);
            if (token != null)
            {
                position.unmarkPosition();
                return token;
            }
            else position.revertPosition();
        }
        return null;
    }
}
