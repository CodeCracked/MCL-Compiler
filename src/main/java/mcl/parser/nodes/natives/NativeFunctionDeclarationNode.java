package mcl.parser.nodes.natives;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.functions.AbstractFunctionDeclarationNode;
import compiler.core.parser.nodes.functions.FunctionSignatureNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.symbols.FunctionSymbol;

import java.util.HashSet;
import java.util.Set;

public class NativeFunctionDeclarationNode extends AbstractFunctionDeclarationNode<FunctionSymbol<NativeFunctionDeclarationNode>>
{
    public final Token nativeFunction;
    public final NativeBindListNode binds;
    
    public NativeFunctionDeclarationNode(Token keyword, FunctionSignatureNode signature, Token nativeFunction, NativeBindListNode binds)
    {
        super(keyword.start(), binds.end(), signature);
        this.nativeFunction = nativeFunction;
        this.binds = binds;
    }
    
    @Override
    protected Result<FunctionSymbol<NativeFunctionDeclarationNode>> instantiateSymbol()
    {
        Result<FunctionSymbol<NativeFunctionDeclarationNode>> result = new Result<>();
    
        // Namespace Retrieval
        NamespaceNode namespace = result.register(findParentNode(NamespaceNode.class));
        if (result.getFailure() != null) return result;
    
        // Symbol Creation
        return result.success(new FunctionSymbol<>(this, namespace.identifier.value, signature.identifier.value));
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Native " + signature.identifier.value + "(binds " + nativeFunction.contents() + ")");
        return childTable;
    }
    
    @Override
    protected Result<Void> validate()
    {
        Result<Void> result = new Result<>();
    
        // Validate Parameter Binds
        Set<String> bindsRequired = new HashSet<>();
        Set<String> bindsCreated = new HashSet<>();
        for (ParameterDeclarationNode parameter : signature.parameters.parameters) bindsRequired.add(parameter.identifier.value);
        for (NativeBindSpecifierNode parameterBind : binds.parameterBinds)
        {
            if (bindsCreated.contains(parameterBind.parameter.value)) result.addError(new CompilerException(parameterBind.start(), parameterBind.end(), "Native function already contains bind for parameter '" + parameterBind.parameter.value + "'!"));
            else if (!bindsRequired.contains(parameterBind.parameter.value)) result.addError(new CompilerException(parameterBind.start(), parameterBind.end(), "Native function does not contain a parameter '" + parameterBind.parameter.value + "' to bind!"));
            else
            {
                bindsCreated.add(parameterBind.parameter.value);
                bindsRequired.remove(parameterBind.parameter.value);
            }
        }
        for (String missingBind : bindsRequired) result.addError(new CompilerException(binds.start(), binds.end(), "Native function missing bind for parameter '" + missingBind + "'!"));
        
        // Validate Return Bind
        if (signature.returnType.value != MCLDataTypes.VOID && binds.returnBind == null) result.addError(new CompilerException(binds.start(), binds.end(), "Native function missing bind for return value!"));
        else if (signature.returnType.value == MCLDataTypes.VOID && binds.returnBind != null) result.addError(new CompilerException(binds.returnBind.start(), binds.returnBind.end(), "Native function contains a bind for a non-existent return value!"));
        
        // Return Result
        if (result.getErrors().size() > 0) return result.failure(new CompilerException(binds.start(), binds.end(), "Native function binds are invalid!"));
        else return result.success(null);
    }
}
