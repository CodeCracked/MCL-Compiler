package mcl.compiler.parser;

import mcl.compiler.exceptions.MCLError;

public class ParseResult
{
    private AbstractNode node;
    private MCLError error;

    public ParseResult()
    {
        this.node = null;
        this.error = null;
    }

    public AbstractNode register(ParseResult result)
    {
        if (result.error != null) error = result.error;
        return result.node;
    }
    public AbstractNode register(AbstractNode node)
    {
        return node;
    }
    public ParseResult success(AbstractNode node)
    {
        this.node = node;
        return this;
    }
    public ParseResult failure(MCLError error)
    {
        this.error = error;
        return this;
    }

    public AbstractNode node() { return node; }
    public MCLError error() { return error; }
}
