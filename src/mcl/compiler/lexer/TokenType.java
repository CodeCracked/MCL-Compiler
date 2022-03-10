package mcl.compiler.lexer;

import java.util.regex.Pattern;

public enum TokenType
{
    INDENT("Indent", "    |\\t"),
    END_OF_LINE("End-of-line", "\\n"),

    // Keywords
    NAMESPACE("namespace"),
    TYPE("type"),
    FUNCTION("func"),
    EVENT("event"),
    LISTENER("listener"),
    TRIGGER("trigger"),
    IS("is"),
    SAFE("safe"),
    UNSAFE("unsafe"),
    WITH("with"),
    INT("int"),
    FLOAT("float"),
    RETURN("return"),

    // Statements
    PRINT("print"),

    // Literals
    FLOAT_LITERAL("Float literal", "[0-9]+\\.[0-9]+", true),
    INT_LITERAL("Int literal", "[0-9]+", true),
    STRING_LITERAL("String literal", "\\\".*\\\"", true),

    // Misc
    SEPARATOR("','", ", *"),
    IDENTIFIER("Identifier", "[a-z]+([a-zA-Z0-9]*)(_[a-zA-Z0-9]+)*", true),
    ;

    private final String print;
    private final Pattern regex;
    private final boolean complex;

    TokenType(String keyword)
    {
        this(keyword, keyword, false);
    }
    TokenType(String print, String regex)
    {
        this(print, regex, false);
    }
    TokenType(String print, String regex, boolean complex)
    {
        this.print = print;
        this.regex = Pattern.compile(regex);
        this.complex = complex;
    }

    public String getPrintableName()
    {
        return print;
    }
    public Pattern getRegex()
    {
        return regex;
    }
    public boolean isComplex()
    {
        return complex;
    }
}
