package mcl.parser.nodes.natives;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;
import compiler.core.parser.nodes.components.DataTypeNode;
import compiler.core.parser.nodes.components.IdentifierNode;
import compiler.core.parser.nodes.components.ParameterDeclarationNode;
import compiler.core.parser.nodes.components.ParameterListNode;
import compiler.core.parser.symbols.SymbolTable;
import compiler.core.util.Result;
import compiler.core.util.exceptions.CompilerException;
import mcl.lexer.MCLDataTypes;

import java.util.HashSet;
import java.util.Set;

public class NativeMethodDeclarationNode extends AbstractNode
{
    public final DataTypeNode returnType;
    public final IdentifierNode identifier;
    public final ParameterListNode parameters;
    public final Token nativeFunction;
    public final NativeBindListNode binds;
    
    private SymbolTable childTable;
    
    public NativeMethodDeclarationNode(Token keyword, DataTypeNode returnType, IdentifierNode identifier, ParameterListNode parameters, Token nativeFunction, NativeBindListNode binds)
    {
        super(keyword.start(), binds.end());
        this.returnType = returnType;
        this.identifier = identifier;
        this.parameters = parameters;
        this.nativeFunction = nativeFunction;
        this.binds = binds;
    }
    
    @Override
    protected SymbolTable getChildSymbolTable(AbstractNode child)
    {
        if (childTable == null) childTable = symbolTable().createChildTable("Native " + identifier.value + "(binds " + nativeFunction.contents() + ")");
        return childTable;
    }
    
    @Override
    protected Result<Void> createSymbols()
    {
        return parameters.createParameterSymbols();
    }
    
    @Override
    protected Result<Void> validate()
    {
        Result<Void> result = new Result<>();
    
        // Validate Parameter Binds
        Set<String> bindsRequired = new HashSet<>();
        Set<String> bindsCreated = new HashSet<>();
        for (ParameterDeclarationNode parameter : parameters.parameters) bindsRequired.add(parameter.identifier.value);
        for (NativeBindSpecifierNode parameterBind : binds.parameterBinds)
        {
            if (bindsCreated.contains(parameterBind.parameter.value)) result.addError(new CompilerException(parameterBind.start(), parameterBind.end(), "Native method already contains bind for parameter '" + parameterBind.parameter.value + "'!"));
            else if (!bindsRequired.contains(parameterBind.parameter.value)) result.addError(new CompilerException(parameterBind.start(), parameterBind.end(), "Native method does not contain a parameter '" + parameterBind.parameter.value + "' to bind!"));
            else
            {
                bindsCreated.add(parameterBind.parameter.value);
                bindsRequired.remove(parameterBind.parameter.value);
            }
        }
        for (String missingBind : bindsRequired) result.addError(new CompilerException(binds.start(), binds.end(), "Native method missing bind for parameter '" + missingBind + "'!"));
        
        // Validate Return Bind
        if (returnType.value != MCLDataTypes.VOID && binds.returnBind == null) result.addError(new CompilerException(binds.start(), binds.end(), "Native method missing bind for return value!"));
        else if (returnType.value == MCLDataTypes.VOID && binds.returnBind != null) result.addError(new CompilerException(binds.returnBind.start(), binds.returnBind.end(), "Native method contains a bind for a non-existent return value!"));
        
        // Return Result
        if (result.getErrors().size() > 0) return result.failure(new CompilerException(binds.start(), binds.end(), "Native method binds are invalid!"));
        else return result.success(null);
    }
}
