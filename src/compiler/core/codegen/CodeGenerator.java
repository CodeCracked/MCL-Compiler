package compiler.core.codegen;

import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.RootNode;
import compiler.core.util.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class CodeGenerator
{
    private record RuleEntry<T extends AbstractNode>(Predicate<AbstractNode> predicate, ICodeGenRule<T> rule) {}
    
    private final List<RuleEntry<?>> rules = new ArrayList<>();
    
    //region Creation
    protected CodeGenerator() { addDefaultRules(); }
    
    protected abstract void addDefaultRules();
    
    public static CodeGenerator empty() { return new CodeGenerator() { @Override protected void addDefaultRules() {} }; }
    //endregion
    //region Configuration
    public <T extends AbstractNode> CodeGenerator addRule(Predicate<AbstractNode> predicate, ICodeGenRule<T> rule)
    {
        RuleEntry<T> entry = new RuleEntry<>(predicate, rule);
        this.rules.add(entry);
        return this;
    }
    public <T extends AbstractNode> CodeGenerator addRule(Class<T> clazz, ICodeGenRule<T> rule)
    {
        return addRule(node -> node.getClass().equals(clazz), rule);
    }
    //endregion
    //region Utilities
    private void recursiveDelete(File file)
    {
        if (file.isFile()) file.delete();
        else if (file.isDirectory())
        {
            for (File child : Objects.requireNonNull(file.listFiles())) recursiveDelete(child);
            file.delete();
        }
    }
    //endregion
    
    public Result<Void> generate(RootNode ast, Path destination)
    {
        // Clear Existing Contents
        if (destination.toFile().exists()) recursiveDelete(destination.toFile());
        destination.toFile().mkdirs();
        
        // Generate Code
        CodeGenContext context = new CodeGenContext(this, destination);
        return generate(ast, context);
    }
    public <T extends AbstractNode> Result<Void> generate(T node, CodeGenContext context)
    {
        if (!node.hasCodeGen()) return Result.of(null);
        
        try
        {
            for (RuleEntry<?> rule : rules) if (rule.predicate.test(node)) return ((RuleEntry<T>) rule).rule.generate(node, context);
            return Result.fail(new UnsupportedOperationException("Code generator does not contain a predicate matching node: " + node));
        }
        catch (IOException e) { return Result.fail(e); }
    }
}
