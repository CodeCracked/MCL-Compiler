package compiler.core.lexer.builders;

import compiler.core.lexer.TokenBuilderList;

import java.util.function.Function;

public class KeywordTokenBuilder extends TokenBuilderList
{
    public static <T extends Enum<?>> KeywordTokenBuilder from(T[] keywords) { return from(keywords, e -> e.name().toLowerCase()); }
    public static <T extends Enum<?>> KeywordTokenBuilder from(T[] keywords, Function<Enum<?>, String> keywordMapper)
    {
        KeywordTokenBuilder builder = new KeywordTokenBuilder();
        for (T keyword : keywords) builder.builders.add(new MatchingTokenBuilder(keyword, keywordMapper.apply(keyword)));
        return builder;
    }
}
