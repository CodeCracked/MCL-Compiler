package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;

public interface IExpressionGenRule<T extends AbstractValueNode>
{
    Result<Integer> generate(Ref<Integer> startingRegister, T component, CodeGenContext context) throws IOException;
}