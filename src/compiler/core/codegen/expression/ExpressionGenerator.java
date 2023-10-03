package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.AbstractValueNode;
import compiler.core.parser.nodes.expression.BinaryOperationNode;
import compiler.core.parser.nodes.expression.LiteralNode;
import compiler.core.util.Pair;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ExpressionGenerator
{
    private static int lastWrittenRegister = 0;
    private static final List<Pair<Predicate<AbstractValueNode>, IExpressionGenRule<AbstractValueNode>>> rules = new ArrayList<>();
    
    static
    {
        addRule(LiteralNode.class, new LiteralGenerator());
        addRule(BinaryOperationNode.class, new BinaryOperationGenerator());
    }
    
    public static Result<Void> generate(AbstractNode expression, CodeGenContext context) throws IOException
    {
        // Prepare Generator
        Result<Void> result = new Result<>();
        lastWrittenRegister = 0;
        
        // Print Header
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        file.println("# " + expression.getMCLDescription());
        
        // Run Code Gen
        Ref<Integer> startingRegister = Ref.of(0);
        result.register(generate(startingRegister, (AbstractValueNode) expression, context));
        
        // Return Result
        if (result.getFailure() != null) return result;
        return result.success(null);
    }
    public static Result<Integer> generate(Ref<Integer> startingRegister, AbstractValueNode component, CodeGenContext context) throws IOException
    {
        for (Pair<Predicate<AbstractValueNode>, IExpressionGenRule<AbstractValueNode>> entry : rules)
        {
            if (entry.first().test(component))
            {
                return entry.second().generate(startingRegister, component, context);
            }
        }
        return Result.fail(new UnsupportedOperationException("Expression generator does not contain a predicate matching node: " + component));
    }
    
    public static <T extends AbstractValueNode> void addRule(Class<T> clazz, IExpressionGenRule<T> rule) { addRule(test -> clazz.isAssignableFrom(test.getClass()), rule); }
    public static <T extends AbstractValueNode> void addRule(Predicate<AbstractValueNode> predicate, IExpressionGenRule<T> rule) { rules.add(new Pair<>(predicate, (startingRegister, component, context) -> rule.generate(startingRegister, (T)component, context))); }
    public static int getLastWrittenRegister() { return lastWrittenRegister; }
}
