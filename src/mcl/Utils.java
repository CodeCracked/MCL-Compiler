package mcl;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utils
{
    public static <K, V> Map<K, V> map(Object[][] contents)
    {
        return Stream.of(contents).collect(Collectors.toUnmodifiableMap(data -> (K)data[0], data -> (V)data[1]));
    }
}
