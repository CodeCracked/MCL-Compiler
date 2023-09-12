package compiler.core.codegen;

import compiler.core.parser.AbstractNode;
import compiler.core.util.Result;

import java.io.IOException;

public interface ICodeGenRule<T extends AbstractNode>
{
    Result<Void> generate(T node, CodeGenContext context) throws IOException;
}
