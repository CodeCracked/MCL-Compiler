package compiler.core.util;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Contains wrapper methods for printing. Currently, these only redirect to System.out, but
 * will allow for easily printing to a Swing window in the future.
 */
public class IO
{
    private static final Scanner defaultInput = new Scanner(System.in);
    
    public static final PrintStream Warnings = System.err;
    public static final PrintStream Errors = System.err;
    public static PrintStream Output = System.out;
    public static final PrintStream Debug = System.out;
    public static IInput Input = callback -> callback.accept(defaultInput.nextLine());
    
    public interface IInput
    {
        void readLine(Consumer<String> callback);
    }
}