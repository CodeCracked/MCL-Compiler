package mcl.parser.nodes.natives;

import compiler.core.lexer.Token;
import compiler.core.parser.AbstractNode;

import java.util.ArrayList;
import java.util.List;

public class NativeStatementNode extends AbstractNode
{
    public final String[] nativeCommands;
    
    public NativeStatementNode(Token keyword, Token nativeCommands, Token semicolon)
    {
        super(keyword.start(), semicolon.end());
        
        String[] split = nativeCommands.contents().toString().split("\n");
        List<String> commands = new ArrayList<>();
        boolean nonEmptyStringFound = false;
        int trailingEmptyStrings = 0;
        for (String command : split)
        {
            command = command.trim();
            if (command.equals(""))
            {
                if (nonEmptyStringFound) commands.add(command);
                trailingEmptyStrings++;
            }
            else
            {
                trailingEmptyStrings = 0;
                nonEmptyStringFound = true;
                commands.add(command);
            }
        }
        this.nativeCommands = new String[commands.size() - trailingEmptyStrings];
        for (int i = 0; i < this.nativeCommands.length; i++) this.nativeCommands[i] = commands.get(i);
    }
}
