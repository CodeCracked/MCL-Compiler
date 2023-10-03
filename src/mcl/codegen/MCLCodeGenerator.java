package mcl.codegen;

import compiler.core.codegen.CodeGenerator;
import compiler.core.parser.nodes.RootNode;
import compiler.core.parser.nodes.components.BlockNode;
import compiler.core.parser.nodes.components.VariableDeclarationNode;
import mcl.codegen.adapters.FloatDataTypeAdapter;
import mcl.codegen.adapters.IntegerDataTypeAdapter;
import mcl.codegen.rules.*;
import mcl.codegen.rules.symbols.EventGenerator;
import mcl.lexer.MCLDataTypes;
import mcl.parser.nodes.MCLSourceNode;
import mcl.parser.nodes.NamespaceNode;
import mcl.parser.nodes.declarations.ListenerDeclarationNode;
import mcl.parser.nodes.statements.NativeStatementNode;
import mcl.parser.symbols.EventSymbol;

import static mcl.MCL.FLOAT_DECIMAL_PLACES;

public class MCLCodeGenerator extends CodeGenerator
{
    @Override
    protected void addDefaultRules()
    {
        addNodeRule(RootNode.class, new RootGenerator());
        addNodeRule(MCLSourceNode.class, new MCLSourceGenerator());
        addNodeRule(NamespaceNode.class, new NamespaceGenerator());
        addNodeRule(ListenerDeclarationNode.class, new ListenerDeclarationGenerator());
        addNodeRule(BlockNode.class, new BlockGenerator());
        addNodeRule(NativeStatementNode.class, new NativeStatementGenerator());
        addNodeRule(VariableDeclarationNode.class, new VariableDeclarationGenerator());
        
        addSymbolRule(EventSymbol.class, new EventGenerator());
        
        addDataTypeAdapter(MCLDataTypes.INTEGER, new IntegerDataTypeAdapter());
        addDataTypeAdapter(MCLDataTypes.FLOAT, new FloatDataTypeAdapter(FLOAT_DECIMAL_PLACES));
    }
}
