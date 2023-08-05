package compiler.core.source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public abstract class CodeSource
{
    /**
     * Attempt to advance a given source position using this CodeSource
     * @param position The position to advance
     * @return The next position in the {@link CodeSource}, or null if there is none
     */
    abstract SourcePosition advance(SourcePosition position);
    
    /**
     * Get the character at a given {@link SourcePosition} in this {@link CodeSource}
     * @param position The {@link SourcePosition} to advance
     * @return The character at the given position
     */
    abstract char getCharAt(SourcePosition position);
    
    //region Implementations
    private static class Lines extends CodeSource
    {
        private final String[] lines;
        
        public Lines(String... lines)
        {
            this.lines = lines;
            for (int i = 0; i < this.lines.length; i++) this.lines[i] += '\n';
        }
    
        @Override
        public SourcePosition advance(SourcePosition position)
        {
            // Increment Column
            int line = position.line;
            int column = position.column + 1;
        
            // If the position reached the end of the current line
            if (column >= lines[line].length())
            {
                // Increment line, reset column
                line++;
                column = 0;
            
                // If the position reached the end of the source, return null. Otherwise, return the next position
                if (line >= lines.length) return null;
                else return new SourcePosition(this, position.sourceIndex, line, column);
            }
        
            // Return the new position
            else return new SourcePosition(this, position.sourceIndex, line, column);
        }
    
        @Override
        public char getCharAt(SourcePosition position)
        {
            return lines[position.line].charAt(position.column);
        }
    }
    
    /**
     * An implementation of {@link CodeSource} that uses a String literal as source code
     */
    public static class Literal extends Lines
    {
        private final UUID id;
        
        public Literal(String code)
        {
            super(code.split("\n"));
            this.id = UUID.randomUUID();
        }
    
        @Override
        public java.lang.String toString()
        {
            return "CodeSource.String[" + id + "]";
        }
    }
    
    public static class File extends Lines
    {
        private final Path path;
        
        public File(Path path) throws IOException { this(path, null); }
        public File(Path path, Path root) throws IOException
        {
            super(Files.readAllLines(path).toArray(String[]::new));
            this.path = root != null ? root.relativize(path) : path;
        }
    
        @Override
        public String toString()
        {
            return "File '" + path + "'";
        }
    }
    //endregion
}
