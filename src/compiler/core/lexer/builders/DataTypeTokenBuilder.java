package compiler.core.lexer.builders;

import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.TokenBuilderList;
import compiler.core.lexer.types.TokenType;
import compiler.core.source.SourcePosition;
import compiler.core.types.DataType;
import compiler.core.types.DataTypeList;

public class DataTypeTokenBuilder extends TokenBuilderList
{
    private final DataTypeList dataTypes;
    
    public DataTypeTokenBuilder(DataTypeList dataTypes)
    {
        this.dataTypes = dataTypes;
        for (DataType type : dataTypes.dataTypes()) this.builders.add(new MatchingTokenBuilder(TokenType.DATA_TYPE, type.keyword()));
    }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        Token baseToken = super.tryBuild(lexer, position);
        if (baseToken != null)
        {
            DataType type = this.dataTypes.lookupKeyword((String)baseToken.contents());
            if (type != null) return new Token(TokenType.DATA_TYPE, type, baseToken.start(), baseToken.end());
        }
        return null;
    }
}
