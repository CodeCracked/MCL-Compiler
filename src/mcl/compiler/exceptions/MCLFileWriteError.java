package mcl.compiler.exceptions;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class MCLFileWriteError extends MCLError
{
    private final IOException ioException;

    public MCLFileWriteError(IOException ioException)
    {
        super(null, null, "", "");
        this.ioException = ioException;
    }

    @Override
    public String getMessage()
    {
        return ioException.getMessage();
    }

    @Override
    public void printStackTrace()
    {
        ioException.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s)
    {
        ioException.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s)
    {
        ioException.printStackTrace(s);
    }

    @Override
    public String toString()
    {
        return ioException.getMessage();
    }
}
