package mcl.compiler.analyzer;

import mcl.compiler.CompilerConfig;
import mcl.compiler.MCLKeywords;

import java.util.*;

public class RuntimeType
{
    public static final RuntimeType VOID = new RuntimeType("void", "!!!MCL_INTERNAL_ERROR!!!", 0);
    public static final RuntimeType INTEGER = new RuntimeType(MCLKeywords.INT, "int", 0);
    public static final RuntimeType FLOAT = new RuntimeType(MCLKeywords.FLOAT, "float", 1);
    public static final RuntimeType UNDEFINED = new RuntimeType("undefined", "!!!MCL_INTERNAL_ERROR!!!", Integer.MAX_VALUE);

    // region Configuration
    private static final Map<RuntimeType, Set<RuntimeType>> combinableTypes = new HashMap<>();
    private static final Map<RuntimeType, Set<RuntimeType>> implicitCasts = new HashMap<>();

    static
    {
        VOID.setCombinableTypes();
        INTEGER.setCombinableTypes(FLOAT);
        FLOAT.setCombinableTypes(INTEGER);
        UNDEFINED.setCombinableTypes();

        VOID.setImplicitCasts();
        INTEGER.setImplicitCasts(FLOAT);
        FLOAT.setImplicitCasts();
        UNDEFINED.setImplicitCasts();
    }

    private void setCombinableTypes(RuntimeType... types)
    {
        Set<RuntimeType> set = new HashSet<>();
        Collections.addAll(set, types);
        set.add(this);
        combinableTypes.put(this, Collections.unmodifiableSet(set));
    }
    private void setImplicitCasts(RuntimeType... casts)
    {
        Set<RuntimeType> set = new HashSet<>();
        Collections.addAll(set, casts);
        set.add(this);
        implicitCasts.put(this, Collections.unmodifiableSet(set));
    }
    // endregion

    private final String keyword;
    private final String minecraftName;
    private final int priority;

    private RuntimeType(String keyword, String minecraftName, int priority)
    {
        this.keyword = keyword;
        this.minecraftName = minecraftName;
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
        if (this.equals(other)) return this;
        else if (combinableTypes.get(this).contains(other)) return this.priority > other.priority ? this : other;
        else return UNDEFINED;
    }

    public boolean isAssignableFrom(RuntimeType other)
    {
        if (this.equals(other)) return true;
        else return implicitCasts.get(other).contains(this);
    }

    public String scaleUp(CompilerConfig config)
    {
        if (this.equals(INTEGER)) return "1";
        else if (this.equals(FLOAT)) return config.floatScaleUp();
        else return "!!!MCL_INTERNAL_ERROR!!!";
    }
    public String scaleDown(CompilerConfig config)
    {
        if (this.equals(INTEGER)) return "1";
        else if (this.equals(FLOAT)) return config.floatScaleDown();
        else return "!!!MCL_INTERNAL_ERROR!!!";
    }
    public String getMinecraftName() { return minecraftName; }

    // region Overrides
    @Override
    public int hashCode()
    {
        return keyword.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof RuntimeType type) return keyword.equals(type.keyword);
        else return false;
    }

    @Override
    public String toString()
    {
        return keyword;
    }
    // endregion
}
