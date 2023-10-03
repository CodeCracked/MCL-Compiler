package compiler.core.codegen;

public interface ICompilable
{
    default boolean compileEnabled() { return true; }
}
