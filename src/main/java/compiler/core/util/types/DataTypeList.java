package compiler.core.util.types;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class DataTypeList
{
    private final Map<String, DataType> keywordMap;
    private final Map<Enum<?>, DataType> literalTypeMap;
    
    private DataTypeList(Collection<DataType> dataTypes)
    {
        this.keywordMap = new HashMap<>();
        this.literalTypeMap = new HashMap<>();
        for (DataType dataType : dataTypes)
        {
            this.keywordMap.put(dataType.keyword(), dataType);
            this.literalTypeMap.put(dataType.literalType(), dataType);
        }
    }
    
    //region Creation
    public static DataTypeList of(DataType... dataTypes)
    {
        List<DataType> types = new ArrayList<>();
        Collections.addAll(types, dataTypes);
        return new DataTypeList(types);
    }
    public static DataTypeList create(Class<?>... typeSets)
    {
        // Load the data types from the provided class
        List<DataType> types = new ArrayList<>();
        try
        {
            for (Class<?> typeSet : typeSets)
            {
                for (Field field : typeSet.getDeclaredFields())
                {
                    if (Modifier.isStatic(field.getModifiers()) && DataType.class.isAssignableFrom(field.getType()))
                    {
                        field.setAccessible(true);
                        DataType dataType = (DataType) field.get(null);
                        assert dataType != null;
                        types.add(dataType);
                    }
                }
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
        
        // Create the token builder
        return new DataTypeList(types);
    }
    //endregion
    //region Iteration
    public Iterable<DataType> dataTypes() { return keywordMap.values(); }
    //endregion
    //region Querying
    public DataType lookupKeyword(String keyword) { return keywordMap.getOrDefault(keyword, DataType.UNKNOWN); }
    public DataType lookupLiteralType(Enum<?> tokenType) { return literalTypeMap.getOrDefault(tokenType, DataType.UNKNOWN); }
    //endregion
}
