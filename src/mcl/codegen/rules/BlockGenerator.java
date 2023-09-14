package mcl.codegen.rules;

import compiler.core.codegen.CodeGenContext;
import compiler.core.codegen.ICodeGenRule;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.util.Result;

import java.io.IOException;

public class BlockGenerator implements ICodeGenRule<BlockNode>
{
    @Override
    public Result<Void> generate(BlockNode node, CodeGenContext context) throws IOException
    {
        Result<Void> result = new Result<>();
        
        // Generate children
        for (AbstractNode child : node.children)
        {
            result.register(context.generate(child));
            if (result.getFailure() != null) return result;
        }
        
        return result.success(null);
    }
}
