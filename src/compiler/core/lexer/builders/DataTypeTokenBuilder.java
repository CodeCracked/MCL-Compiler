package compiler.core.lexer.builders;

import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.TokenBuilderList;
import compiler.core.lexer.types.DataType;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourcePosition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class DataTypeTokenBuilder extends TokenBuilderList
{
    private final Map<String, DataType> dataTypes;
    
    private DataTypeTokenBuilder(Collection<DataType> dataTypes)
    {
        this.dataTypes = new HashMap<>();
        for (DataType dataType : dataTypes)
        {
            this.dataTypes.put(dataType.keyword(), dataType);
            this.builders.add(new MatchingTokenBuilder(TokenType.DATA_TYPE, dataType.keyword()));
        }
    }
    
    public static DataTypeTokenBuilder create(Class<?> dataTypes)
    {
        // Load the data types from the provided class
        List<DataType> types = new ArrayList<>();
        try
        {
            for (Field field : dataTypes.getDeclaredFields())
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
        catch (Exception e) { throw new RuntimeException(e); }
        
        // Create the token builder
        return new DataTypeTokenBuilder(types);
    }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        Token baseToken = super.tryBuild(lexer, position);
        if (baseToken != null)
        {
            DataType type = this.dataTypes.get((String)baseToken.contents());
            if (type != null) return new Token(TokenType.DATA_TYPE, type, baseToken.start(), baseToken.end());
        }
        return null;
    }
}
