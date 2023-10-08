package compiler.core.util.types;

import java.util.*;

public class DataType
{
    public interface CastFunction { Object cast(Object value); }
    
    private final String name;
    private final String keyword;
    private final Enum<?> literalType;
    private final Set<DataType> explicitCasts;
    private final Set<DataType> implicitCasts;
    private final Map<DataType, CastFunction> castFunctions;
    
    public static final DataType UNKNOWN = new DataType("UNKNOWN", null, null);
    
    public DataType(String keyword, Enum<?> literalType) { this(keyword, keyword, literalType); }
    public DataType(String name, String keyword, Enum<?> literalType)
    {
        this.name = name;
        this.keyword = keyword;
        this.literalType = literalType;
        this.explicitCasts = new HashSet<>();
        this.implicitCasts = new HashSet<>();
        this.castFunctions = new HashMap<>();
        
        addImplicitCast(this, from -> from);
    }
    
    //region Getters
    public String name() { return name; }
    public String keyword() { return keyword; }
    public Enum<?> literalType() { return literalType; }
    //endregion
    //region Configuration
    public DataType addExplicitCast(DataType castTo, CastFunction castFunction)
    {
        this.explicitCasts.add(castTo);
        this.castFunctions.put(castTo, castFunction);
        return this;
    }
    public DataType addImplicitCast(DataType castTo, CastFunction castFunction)
    {
        this.explicitCasts.add(castTo);
        this.implicitCasts.add(castTo);
        this.castFunctions.put(castTo, castFunction);
        return this;
    }
    //endregion
    //region Properties
    public boolean canImplicitCast(DataType castTo) { return implicitCasts.contains(castTo); }
    public boolean canExplicitCast(DataType castTo) { return explicitCasts.contains(castTo); }
    public CastFunction getCastFunction(DataType castTo) { return castFunctions.get(castTo); }
    //endregion
    
    @Override
    public String toString()
    {
        return "DataType<" + name + ">";
    }
}
