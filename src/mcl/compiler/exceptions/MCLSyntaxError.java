package mcl.compiler.exceptions;

import mcl.compiler.lexer.Token;
import mcl.compiler.parser.MCLParser;
import mcl.compiler.source.CodeLocation;
import mcl.compiler.source.MCLSourceCollection;

public class MCLSyntaxError extends MCLError
{
    public MCLSyntaxError(MCLParser parser, String details)
    {
        this(parser.getSource(), parser.getCurrentToken(), details);
    }
    public MCLSyntaxError(MCLSourceCollection source, Token token, String details)
    {
        this(source.getCodeLocation(token.startPosition()), source.getCodeLocation(token.endPosition()), details);
    }
    public MCLSyntaxError(CodeLocation start, CodeLocation end, String details)
    {
        super(start, end, "Syntax Error", details);
    }
}