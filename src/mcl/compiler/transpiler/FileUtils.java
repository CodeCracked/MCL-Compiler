package mcl.compiler.transpiler;

import java.io.File;

public final class FileUtils
{
    public static void delete(File file, boolean deleteSelf)
    {
        if (file.exists())
        {
            if (file.isDirectory())
            {
                File[] children = file.listFiles();
                for (File child : children) delete(child, true);
            }
            if (deleteSelf) file.delete();
        }
    }
}
