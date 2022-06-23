package mcl.compiler.analyzer;

import java.util.*;

public class RuntimeType
{
    public static final RuntimeType VOID = new RuntimeType("VOID", 0);
    public static final RuntimeType INTEGER = new RuntimeType("INTEGER", 0);
    public static final RuntimeType FLOAT = new RuntimeType("FLOAT", 1);
    public static final RuntimeType UNDEFINED = new RuntimeType("UNDEFINED", Integer.MAX_VALUE);

    private static final Map<RuntimeType, Set<RuntimeType>> implicitCasts = new HashMap<>();
    static
    {
        VOID.setImplicitCasts();
        INTEGER.setImplicitCasts(FLOAT);
        FLOAT.setImplicitCasts(INTEGER);
        UNDEFINED.setImplicitCasts(UNDEFINED);
    }

    private final String name;
    private final int priority;

    private RuntimeType(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
    }

    private void setImplicitCasts(RuntimeType... casts)
    {
        Set<RuntimeType> set = new HashSet<>();
        Collections.addAll(set, casts);
        set.add(this);
        implicitCasts.put(this, Collections.unmodifiableSet(set));
    }

    public RuntimeType getCombinedType(RuntimeType other)
    {
        if (this == other) return this;
        else if (implicitCasts.get(this).contains(other)) return this.priority > other.priority ? this : other;
        else return UNDEFINED;
    }

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
}
