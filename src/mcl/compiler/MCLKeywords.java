package mcl.compiler;

import java.util.Set;

public final class MCLKeywords
{
    public static final String NAMESPACE = "namespace";
    public static final String INT = "int";
    public static final String FLOAT = "float";
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String NOT = "not";

    public static Set<String> TYPES = Set.of(
            INT,
            FLOAT
    );

    public static Set<String> KEYWORDS = Set.of(
            NAMESPACE,
            INT,
            FLOAT,
            AND,
            OR,
            NOT
    );
}
