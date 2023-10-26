package mcl;

import compiler.core.Compiler;
import compiler.core.codegen.CodeGenerator;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.builders.*;
import compiler.core.parser.Parser;
import compiler.core.source.SourceCollection;
import compiler.core.util.Result;
import compiler.core.util.types.DataTypeList;
import mcl.codegen.MCLCodeGenerator;
import mcl.codegen.MCLStandardLibrary;
import mcl.lexer.MCLDataTypes;
import mcl.lexer.MCLKeyword;
import mcl.parser.MCLRules;

public final class MCL
{
    public static final int FLOAT_DECIMAL_PLACES = 3;
    
    private static final DataTypeList DATA_TYPES = DataTypeList.create(MCLDataTypes.class);
    private static final Lexer LEXER = new Lexer
    (
        // Ignored Tokens
        WhitespaceTokenBuilder.ignore(), CommentTokenBuilder.ignore(),
        
        // Literal Tokens
        NumberLiteralsTokenBuilder.normal(),
        StringLiteralTokenBuilder.withRaw(),
        
        // Symbol Tokens
        ComparisonSymbolsTokenBuilder.normal(),
        MathSymbolsTokenBuilder.normal(),
        GrammarSymbolsTokenBuilder.normal(),
        
        // Complex Tokens
        new DataTypeTokenBuilder(DATA_TYPES),
        KeywordTokenBuilder.from(MCLKeyword.values()),
        IdentifierTokenBuilder.camelCase()
    );
    private static final Parser PARSER = Parser.bracedScope(DATA_TYPES, MCLRules.SOURCE_FILE);
    private static final CodeGenerator CODE_GENERATOR = new MCLCodeGenerator();
    private static final Compiler COMPILER = new Compiler(LEXER, PARSER, CODE_GENERATOR, true)
    {
        @Override
        public Result<SourceCollection> finalizeSourceCollection(SourceCollection source)
        {
            return MCLStandardLibrary.installHeaders(source);
        }
    };
    
    public static Lexer lexer() { return LEXER; }
    public static Parser parser() { return PARSER; }
    public static Compiler compiler() { return COMPILER; }
}
