package mcl.codegen;

import compiler.core.codegen.CodeGenerator;
import compiler.core.parser.nodes.RootNode;
import compiler.core.parser.nodes.components.BlockNode;
import mcl.codegen.adapters.FloatDataTypeAdapter;
import mcl.codegen.adapters.IntegerDataTypeAdapter;
import mcl.codegen.rules.*;
import mcl.codegen.rules.nodes.functions.FunctionCallGenerator;
import mcl.codegen.rules.nodes.functions.FunctionCallStatementGenerator;
import mcl.codegen.rules.nodes.functions.FunctionDeclarationGenerator;
import mcl.codegen.rules.nodes.natives.NativeFunctionDeclarationGenerator;
import mcl.codegen.rules.nodes.natives.NativeStatementGenerator;
import mcl.codegen.rules.symbols.EventGenerator;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.nodes.components.FunctionCallNode;
import mcl.parser.nodes.components.VariableAccessNode;
import mcl.parser.nodes.declarations.FunctionDeclarationNode;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;
import mcl.parser.nodes.declarations.VariableDeclarationNode;
import mcl.parser.nodes.natives.NativeFunctionDeclarationNode;
import mcl.parser.nodes.natives.NativeStatementNode;
import mcl.parser.nodes.statements.FunctionCallStatementNode;
import mcl.parser.symbols.EventSymbol;

public class MCLCodeGenerator extends CodeGenerator
{
    @Override
    protected void addDefaultRules()
    {
        addNodeRule(RootNode.class, new RootGenerator());
        addNodeRule(MCLSourceNode.class, new MCLSourceGenerator());
        addNodeRule(NamespaceNode.class, new NamespaceGenerator());
        addNodeRule(ListenerDeclarationNode.class, new ListenerDeclarationGenerator());
        addNodeRule(FunctionDeclarationNode.class, new FunctionDeclarationGenerator());
        addNodeRule(NativeFunctionDeclarationNode.class, new NativeFunctionDeclarationGenerator());
        addNodeRule(BlockNode.class, new BlockGenerator());
        addNodeRule(NativeStatementNode.class, new NativeStatementGenerator());
        addNodeRule(VariableDeclarationNode.class, new VariableDeclarationGenerator());
        addNodeRule(FunctionCallStatementNode.class, new FunctionCallStatementGenerator());
        
        addSymbolRule(EventSymbol.class, new EventGenerator());
        
        addDataTypeAdapter(MCLDataTypes.INTEGER, new IntegerDataTypeAdapter());
        addDataTypeAdapter(MCLDataTypes.FLOAT, new FloatDataTypeAdapter());
        
        getExpressionGenerator().addRule(VariableAccessNode.class, new VariableAccessGenerator());
        getExpressionGenerator().addRule(FunctionCallNode.class, new FunctionCallGenerator());
    }
}
