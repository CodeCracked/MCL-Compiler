package mcl.compiler.syntax.helpers;

import mcl.compiler.exceptions.MCLSyntaxException;
import mcl.compiler.lexer.Token;
import mcl.compiler.lexer.TokenType;
import mcl.compiler.syntax.SyntaxAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class ParameterValueList
{
    public List<TokenType> SEPARATOR_TOKEN_TYPES = List.of(TokenType.SEPARATOR, TokenType.END_OF_LINE);

    private List<Token> values;

    public ParameterValueList(SyntaxAnalyzer syntax) throws MCLSyntaxException
    {
        values = new ArrayList<>();
        do values.add(syntax.nextToken(TokenTypeLists.VALUE_TOKEN_TYPES));
        while (syntax.nextToken(SEPARATOR_TOKEN_TYPES).getTokenType() == TokenType.SEPARATOR);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("values{ ");
        List<String> parameters = new ArrayList<>();
        this.values.forEach(param -> sb.append(param.getToken()));
        sb.append(String.join(", ", parameters));
        sb.append(" }");
        return sb.toString();
    }
}