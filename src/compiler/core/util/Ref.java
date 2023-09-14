package compiler.core.util;

import java.util.Objects;

public final class Ref<T>
{
    private T value;
    
    private Ref(T value) { this.value = value; }
    
    public static <T> Ref<T> of(T value) { return new Ref<>(Objects.requireNonNull(value)); }
    
    public T get() { return value; }
    public void set(T value) { this.value = value; }
}
