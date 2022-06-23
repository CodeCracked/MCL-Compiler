package mcl.compiler.analyzer;

import mcl.compiler.MCLKeywords;

import java.util.*;

public class RuntimeType
{
    public static final RuntimeType VOID = new RuntimeType("VOID", 0);
    public static final RuntimeType INTEGER = new RuntimeType("INTEGER", 0);
    public static final RuntimeType FLOAT = new RuntimeType("FLOAT", 1);
    public static final RuntimeType UNDEFINED = new RuntimeType("UNDEFINED", Integer.MAX_VALUE);

    // region Configuration
    private static final Map<RuntimeType, Set<RuntimeType>> implicitCasts = new HashMap<>();
    static
    {
        VOID.setImplicitCasts();
        INTEGER.setImplicitCasts(FLOAT);
        FLOAT.setImplicitCasts(INTEGER);
        UNDEFINED.setImplicitCasts(UNDEFINED);
    }

    private void setImplicitCasts(RuntimeType... casts)
    {
        Set<RuntimeType> set = new HashSet<>();
        Collections.addAll(set, casts);
        set.add(this);
        implicitCasts.put(this, Collections.unmodifiableSet(set));
    }
    // endregion

    private final String name;
    private final int priority;

    private RuntimeType(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
    }
    public static RuntimeType parse(String type)
    {
        if (type.equals(MCLKeywords.INT)) return INTEGER;
        else if (type.equals(MCLKeywords.FLOAT)) return FLOAT;
        else return UNDEFINED;
    }

    public RuntimeType getCombinedType(RuntimeType other)
    {
        if (this == other) return this;
        else if (implicitCasts.get(this).contains(other)) return this.priority > other.priority ? this : other;
        else return UNDEFINED;
    }

    // region Overrides
    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof RuntimeType type) return name.equals(type.name);
        else return false;
    }

    @Override
    public String toString()
    {
        return name;
    }
    // endregion
}
