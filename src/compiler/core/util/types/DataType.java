package compiler.core.util.types;

import java.util.HashSet;
import java.util.Set;

public class DataType
{
    private final String name;
    private final String keyword;
    private final Enum<?> literalType;
    private final Set<DataType> explicitCasts;
    private final Set<DataType> implicitCasts;
    
    public static final DataType UNKNOWN = new DataType("UNKNOWN", null, null);
    
    public DataType(String keyword, Enum<?> literalType) { this(keyword, keyword, literalType); }
    public DataType(String name, String keyword, Enum<?> literalType)
    {
        this.name = name;
        this.keyword = keyword;
        this.literalType = literalType;
        this.explicitCasts = new HashSet<>();
        this.implicitCasts = new HashSet<>();
    }
    
    //region Getters
    public String name() { return name; }
    public String keyword() { return keyword; }
    public Enum<?> literalType() { return literalType; }
    //endregion
    //region Configuration
    public DataType addExplicitCast(DataType castTo)
    {
        this.explicitCasts.add(castTo);
        return this;
    }
    public DataType addImplicitCast(DataType castTo)
    {
        this.explicitCasts.add(castTo);
        this.implicitCasts.add(castTo);
        return this;
    }
    //endregion
    //region Properties
    public boolean canImplicitCast(DataType castTo) { return castTo == this || implicitCasts.contains(castTo); }
    public boolean canExplicitCast(DataType castTo) { return castTo == this || explicitCasts.contains(castTo); }
    public boolean isAssignableFrom(DataType other) { return canImplicitCast(other); }
    
    public DataType resultWith(DataType other)
    {
        if (this.equals(other)) return this;
        else return UNKNOWN;
    }
    //endregion
    
    @Override
    public String toString()
    {
        return "DataType<" + name + ">";
    }
}
