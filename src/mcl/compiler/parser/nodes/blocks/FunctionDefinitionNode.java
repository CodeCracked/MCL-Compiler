package mcl.compiler.parser.nodes.blocks;

import mcl.compiler.MCLCompiler;
import mcl.compiler.analyzer.RuntimeType;
import mcl.compiler.analyzer.symbols.FunctionSymbol;
import mcl.compiler.exceptions.MCLError;
import mcl.compiler.lexer.Token;
import mcl.compiler.parser.AbstractNode;
import mcl.compiler.parser.nodes.variables.VariableSignatureNode;
import mcl.compiler.source.MCLSourceCollection;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class FunctionDefinitionNode extends AbstractNode
{
    public final UUID symbolTableID;
    public final Token identifier;
    public final List<VariableSignatureNode> parameters;
    public final RuntimeType returnType;
    public final AbstractNode body;

    public FunctionDefinitionNode(Token keyword, Token identifier, List<VariableSignatureNode> parameters, Token returnType, AbstractNode body)
    {
        super(keyword.startPosition(), body.endPosition());

        this.symbolTableID = UUID.randomUUID();
        this.identifier = identifier;
        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType != null ? RuntimeType.parse((String)returnType.value()) : RuntimeType.VOID;
        this.body = body;
    }

    @Override
    public void walk(BiConsumer<AbstractNode, AbstractNode> parentChildConsumer)
    {
        for (VariableSignatureNode parameter : parameters)
        {
            parentChildConsumer.accept(this, parameter);
            parameter.walk(parentChildConsumer);
        }

        parentChildConsumer.accept(this, body);
        body.walk(parentChildConsumer);
    }

    @Override
    public MCLError createSymbols(MCLCompiler compiler, MCLSourceCollection source)
    {
        MCLError error = compiler.getSymbolTable().addSymbol(new FunctionSymbol(identifier, returnType));
        if (error != null) return error;

        compiler.pushSymbolTable(symbolTableID);
        {
            for (VariableSignatureNode parameter : parameters)
            {
                error = parameter.createSymbols(compiler, source);
                if (error != null) return error;
            }

            error = body.createSymbols(compiler, source);
            if (error != null) return error;
        }
        compiler.popSymbolTable();

        return null;
    }

    @Override
    public RuntimeType getRuntimeType(MCLCompiler compiler)
    {
        return RuntimeType.UNDEFINED;
    }

    @Override
    public void debugPrint(int depth)
    {
        System.out.print("  ".repeat(depth));
        System.out.println("FUNC_DEFINITION");

        System.out.print("  ".repeat(depth + 1));
        System.out.println(identifier);

        System.out.print("  ".repeat(depth + 1));
        System.out.println("RETURN:" + returnType.toString());

        for (VariableSignatureNode parameter : parameters) parameter.debugPrint(depth + 1);
        body.debugPrint(depth + 1);
    }
}
