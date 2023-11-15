package compiler.core.codegen.expression;

import compiler.core.codegen.CodeGenContext;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.expression.*;
import compiler.core.util.Pair;
import compiler.core.util.Ref;
import compiler.core.util.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ExpressionGenerator
{
    private final List<Pair<Predicate<AbstractValueNode>, IExpressionGenRule<AbstractValueNode>>> rules = new ArrayList<>();
    
    public ExpressionGenerator()
    {
        addRule(LiteralNode.class, new LiteralGenerator());
        addRule(BinaryOperationNode.class, new BinaryOperationGenerator());
        addRule(CastOperationNode.class, new CastOperationGenerator());
        addRule(UnaryOperationNode.class, new UnaryOperationGenerator());
    }
    
    public Result<Void> generate(AbstractNode expression, CodeGenContext context) throws IOException
    {
        // Prepare Generator
        Result<Void> result = new Result<>();
        
        // Get Open File
        PrintWriter file = result.register(context.getOpenFile());
        if (result.getFailure() != null) return result;
        
        // Run Code Gen
        Ref<Integer> startingRegister = Ref.of(0);
        result.register(generate(startingRegister, (AbstractValueNode) expression, context));
        
        // Return Result
        if (result.getFailure() != null) return result;
        return result.success(null);
    }
    public Result<Integer> generate(Ref<Integer> startingRegister, AbstractValueNode component, CodeGenContext context) throws IOException
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
    
    public <T extends AbstractValueNode> void addRule(Class<T> clazz, IExpressionGenRule<T> rule) { addRule(test -> clazz.isAssignableFrom(test.getClass()), rule); }
    public <T extends AbstractValueNode> void addRule(Predicate<AbstractValueNode> predicate, IExpressionGenRule<T> rule) { rules.add(new Pair<>(predicate, (startingRegister, component, context) -> rule.generate(startingRegister, (T)component, context))); }
}
