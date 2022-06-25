package mcl.compiler.exceptions;

import java.io.IOException;

public class MCLFileWriteError extends MCLError
{
    private final IOException ioException;

    public MCLFileWriteError(IOException ioException)
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
