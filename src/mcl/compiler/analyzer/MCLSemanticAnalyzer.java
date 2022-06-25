package mcl.compiler.analyzer;

import mcl.compiler.MCLCompiler;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.source.MCLSourceCollection;

public class MCLSemanticAnalyzer
{
    private final MCLCompiler compiler;
    private final MCLSourceCollection source;
    private final AbstractNode rootNode;

    public MCLSemanticAnalyzer(MCLCompiler compiler, MCLSourceCollection source, AbstractNode rootNode)
    {
        this.compiler = compiler;
        this.source = source;
        this.rootNode = rootNode;
    }

    public MCLError analyze()
    {
        MCLError error = rootNode.createSymbols(compiler, source);
        if (error != null) return error;
        return rootNode.symbolAnalysis(compiler, source);
    }
}
