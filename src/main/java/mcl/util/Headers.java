package mcl.util;

import compiler.core.parser.AbstractNode;

import java.io.PrintWriter;

public final class Headers
{
    public static void writeNodeHeader(AbstractNode node, PrintWriter file)
    {
        file.println("# " + node.start());
        String[] lines = node.getSourceCode().split("\n");
        for (String line : lines) file.println("# " + line.stripTrailing());
    }
}
