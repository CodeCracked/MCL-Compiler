package compiler.core.util.types;

import java.util.HashMap;
import java.util.Map;

public class DataType
{
    private final String name;
    private final String keyword;
    private final Enum<?> literalType;
    private final Map<DataType, DataType> implicitCasts = new HashMap<>();
    
    public static final DataType UNKNOWN = new DataType("UNKNOWN", null, null);
    
    public DataType(String keyword, Enum<?> literalType) { this(keyword, keyword, literalType); }
    public DataType(String name, String keyword, Enum<?> literalType)
    {
        this.name = name;
        this.keyword = keyword;
        this.literalType = literalType;
    }
    
    public String name() { return name; }
    public String keyword() { return keyword; }
    public Enum<?> literalType() { return literalType; }
    
    public boolean isAssignableFrom(DataType other)
    {
        if (this.equals(other)) return true;
        else return false;
    }
    public DataType resultWith(DataType other)
    {
        if (this.equals(other)) return this;
        //else if (implicitCasts.containsKey(other)) return implicitCasts.get(other);
        else return UNKNOWN;
    }
    
    @Override
    public String toString()
    {
        return "DataType<" + name + ">";
    }
}
