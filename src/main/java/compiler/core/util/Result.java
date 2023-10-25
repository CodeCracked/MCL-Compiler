package compiler.core.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result<T>
{
    private T value;
    private Exception failure;
    private int advanceCount = 0;
    private final List<Exception> warnings;
    private final List<Exception> errors;
    
    public Result()
    {
        this.value = null;
        this.failure = null;
        this.warnings = new ArrayList<>();
        this.errors = new ArrayList<>();
    }
    
    public static <T> Result<T> of(T value)
    {
        Result<T> result = new Result<>();
        return result.success(value);
    }
    public static <T> Result<T> fail(Exception e)
    {
        Result<T> result = new Result<>();
        return result.failure(e);
    }
    
    public <T1> T1 register(Result<T1> result)
    {
        registerIssues(result);
        return result.value;
    }
    public <T1> Result<T1> appendIssues(Result<T1> result)
    {
        advanceCount += result.advanceCount;
        warnings.addAll(result.warnings);
        errors.addAll(result.errors);
        if (result.failure != null) errors.add(result.failure);
        return result;
    }
    public <T1> Result<T1> registerIssues(Result<T1> result)
    {
        advanceCount += result.advanceCount;
        warnings.addAll(result.warnings);
        errors.addAll(result.errors);
        if (result.failure != null) failure = result.failure;
        return result;
    }
    
    public void registerAdvancement()
    {
        advanceCount++;
    }
    public void addWarning(Exception warning) { this.warnings.add(warning); }
    public void addError(Exception error) { this.errors.add(error); }
    
    public Result<T> success(T value)
    {
        this.value = value;
        return this;
    }
    public Result<T> failure(Exception error)
    {
        if (this.failure == null || advanceCount == 0) this.failure = error;
        return this;
    }
    
    public T get() { return value; }
    public List<Exception> getWarnings() { return Collections.unmodifiableList(warnings); }
    public List<Exception> getErrors() { return Collections.unmodifiableList(errors); }
    public Exception getFailure() { return failure; }
    
    public void displayIssues(boolean includeStackTrace)
    {
        for (Exception warning : warnings)
        {
            IO.Warnings.println(warning.getMessage());
            if (includeStackTrace) warning.printStackTrace();
        }
        for (Exception error : errors)
        {
            IO.Errors.println(error.getMessage());
            if (includeStackTrace) error.printStackTrace();
        }
        if (failure != null)
        {
            IO.Errors.println(failure.getMessage());
            if (includeStackTrace) failure.printStackTrace();
        }
    }
}