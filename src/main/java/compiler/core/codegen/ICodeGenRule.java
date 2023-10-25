package compiler.core.codegen;

import compiler.core.util.Result;

import java.io.IOException;

public interface ICodeGenRule<T>
{
    Result<Void> generate(T component, CodeGenContext context) throws IOException;
}
