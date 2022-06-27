package mcl.compiler;

import java.util.Set;

public final class MCLKeywords
{
    public static final String NAMESPACE = "namespace";
    public static final String FUNC = "func";
    public static final String EVENT = "event";
    public static final String LISTENER = "listener";

    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String RUN = "run";
    public static final String RETURN = "return";

    public static final String INT = "int";
    public static final String FLOAT = "float";

    public static final String AND = "and";
    public static final String OR = "or";
    public static final String NOT = "not";

    public static Set<String> VARIABLE_TYPES = Set.of(
            INT,
            FLOAT
    );

    public static Set<String> KEYWORDS = Set.of(
            NAMESPACE,
            FUNC,
            EVENT,
            LISTENER,

            IF,
            ELSE,
            RUN,
            RETURN,

            INT,
            FLOAT,

            AND,
            OR,
            NOT
    );
}
