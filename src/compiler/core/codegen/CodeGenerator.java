package compiler.core.codegen;

import compiler.core.codegen.expression.ExpressionGenerator;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.RootNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.symbols.AbstractSymbol;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.types.DataType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public abstract class CodeGenerator
{
    private record NodeRuleEntry<T extends AbstractNode>(Predicate<AbstractNode> predicate, ICodeGenRule<T> rule) {}
    private record SymbolRuleEntry<T extends AbstractSymbol>(Predicate<AbstractSymbol> predicate, ICodeGenRule<T> rule) {}
    
    private final List<NodeRuleEntry<?>> nodeRules = new ArrayList<>();
    private final List<SymbolRuleEntry<?>> symbolRules = new ArrayList<>();
    private final Map<DataType, DataTypeCodeAdapter> dataTypeAdapters = new HashMap<>();
    
    //region Creation
    protected CodeGenerator()
    {
        addNodeRule(node -> AbstractValueNode.class.isAssignableFrom(node.getClass()), ExpressionGenerator::generate);
        addDefaultRules();
    }
    
    protected abstract void addDefaultRules();
    
    public static CodeGenerator empty() { return new CodeGenerator() { @Override protected void addDefaultRules() {} }; }
    //endregion
    //region Configuration
    public <T extends AbstractNode> CodeGenerator addNodeRule(Predicate<AbstractNode> predicate, ICodeGenRule<T> rule)
    {
        NodeRuleEntry<T> entry = new NodeRuleEntry<>(predicate, rule);
        this.nodeRules.add(entry);
        return this;
    }
    public <T extends AbstractNode> CodeGenerator addNodeRule(Class<T> clazz, ICodeGenRule<T> rule)
    {
        return addNodeRule(node -> node.getClass().equals(clazz), rule);
    }
    
    public <T extends AbstractSymbol> CodeGenerator addSymbolRule(Predicate<AbstractSymbol> predicate, ICodeGenRule<T> rule)
    {
        SymbolRuleEntry<T> entry = new SymbolRuleEntry<>(predicate, rule);
        this.symbolRules.add(entry);
        return this;
    }
    public <T extends AbstractSymbol> CodeGenerator addSymbolRule(Class<T> clazz, ICodeGenRule<T> rule)
    {
        return addSymbolRule(symbol -> symbol.getClass().equals(clazz), rule);
    }
    
    public CodeGenerator addDataTypeAdapter(DataType type, DataTypeCodeAdapter adapter)
    {
        this.dataTypeAdapters.put(type, adapter);
        return this;
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
        Result<Void> result = new Result<>();
        
        // Clear Existing Contents
        if (destination.toFile().exists()) recursiveDelete(destination.toFile());
        destination.toFile().mkdirs();
        
        // Generate AST Code
        CodeGenContext context = new CodeGenContext(this, destination);
        result.register(generate(ast, context));
        if (result.getFailure() != null) return result;
        
        // Generate Symbol Table Code
        List<SymbolTable.SymbolEntry<AbstractSymbol>> symbols = result.register(ast.symbolTable()
                .collectByInterface(ast, AbstractSymbol.class, ICompilable.class)
                .filterSelf(entry -> ((ICompilable)entry.symbol()).compileEnabled())
                .any(true));
        for (SymbolTable.SymbolEntry<AbstractSymbol> symbol : symbols)
        {
            result.register(generate(symbol.symbol(), context));
            if (result.getFailure() != null) return result;
        }
        
        return result;
    }
    
    public <T extends AbstractNode> Result<Void> generate(T node, CodeGenContext context)
    {
        if (!node.hasCodeGen()) return Result.of(null);
        
        try
        {
            for (NodeRuleEntry<?> rule : nodeRules)
            {
                if (rule.predicate.test(node))
                {
                    context.openSnapshot();
                    Result<Void> result = ((NodeRuleEntry<T>) rule).rule.generate(node, context);
                    context.closeSnapshot();
                    return result;
                }
            }
            return Result.fail(new UnsupportedOperationException("Code generator does not contain a predicate matching node: " + node));
        }
        catch (IOException e) { return Result.fail(e); }
    }
    public <T extends AbstractSymbol> Result<Void> generate(T symbol, CodeGenContext context)
    {
        try
        {
            for (SymbolRuleEntry<?> rule : symbolRules)
            {
                if (rule.predicate.test(symbol))
                {
                    context.openSnapshot();
                    Result<Void> result = ((SymbolRuleEntry<T>) rule).rule.generate(symbol, context);
                    context.closeSnapshot();
                    return result;
                }
            }
            return Result.fail(new UnsupportedOperationException("Code generator does not contain a predicate matching symbol: " + symbol));
        }
        catch (IOException e) { return Result.fail(e); }
    }
    public Result<DataTypeCodeAdapter> getTypeAdapter(DataType dataType)
    {
        if (this.dataTypeAdapters.containsKey(dataType)) return Result.of(this.dataTypeAdapters.get(dataType));
        else return Result.fail(new UnsupportedOperationException("Code generator does not contain an adapter for DataType " + dataType.name() + "!"));
    }
}
