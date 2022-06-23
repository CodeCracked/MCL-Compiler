package mcl.compiler;

import java.util.Set;

public final class MCLKeywords
{
    public static final String INT_KEYWORD = "int";
    public static final String FLOAT_KEYWORD = "float";

    public static Set<String> KEYWORDS = Set.of(
            INT_KEYWORD,
            FLOAT_KEYWORD
    );
}
