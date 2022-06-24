package mcl.compiler.exceptions;

import java.io.IOException;

public class MCLFileAppendError extends MCLError
{
    private final IOException ioException;

    public MCLFileAppendError(IOException ioException)
    {
        super(null, null, "", "");
        this.ioException = ioException;
    }

    @Override
    public String toString()
    {
        return ioException.getMessage();
    }
}
