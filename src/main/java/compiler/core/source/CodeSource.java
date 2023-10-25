package compiler.core.source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public abstract class CodeSource
{
    //region Interface
    /**
     * Move a given source position to the start of this CodeSource
     * @param position The {@link SourcePosition} to move
     */
    abstract void moveToStart(SourcePosition position);
    
    /**
     * Move a given source position to the end of this CodeSource
     * @param position The {@link SourcePosition} to move
     */
    abstract void moveToEnd(SourcePosition position);
    
    /**
     * @return True if this CodeSource doesn't contain anything, false otherwise
     */
    abstract boolean empty();
    
    /**
     * Attempt to advance a given source position using this CodeSource
     * @param position The position to advance
     * @return True if the advance was successful, false otherwise
     */
    abstract boolean advance(SourcePosition position);
    
    /**
     * Attempt to retract a given source position using this CodeSource
     * @param position The position to retract
     * @return True if the retraction was successful, false otherwise
     */
    abstract boolean retract(SourcePosition position);
    
    /**
     * Check whether the source position is a valid position within this CodeSource
     * @param position The position to check
     * @return True if the position is valid, false otherwise
     */
    abstract boolean valid(SourcePosition position);
    
    /**
     * Get the character at a given {@link SourcePosition} in this {@link CodeSource}
     * @param position The {@link SourcePosition} to advance
     * @return The character at the given position
     */
    abstract char charAt(SourcePosition position);
    //endregion
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
        void moveToStart(SourcePosition position)
        {
            position.line = 0;
            position.column = 0;
        }
    
        @Override
        void moveToEnd(SourcePosition position)
        {
            position.line = lines.length - 1;
            position.column = lines[position.line].length() - 1;
        }
    
        @Override
        boolean empty()
        {
            for (String line : lines) if (line.trim().length() > 0) return false;
            return true;
        }
    
        @Override
        public boolean advance(SourcePosition position)
        {
            position.column++;
            if (position.column >= lines[position.line].length())
            {
                position.line++;
                position.column = 0;
                return position.line < lines.length;
            }
            return true;
        }
    
        @Override
        boolean retract(SourcePosition position)
        {
            position.column--;
            if (position.column < 0)
            {
                position.line--;
                if (position.line < 0) return false;
                else position.column = lines[position.line].length() - 1;
            }
            return true;
        }
    
        @Override
        boolean valid(SourcePosition position)
        {
            return position.line >= 0 && position.line < lines.length;
        }
    
        @Override
        public char charAt(SourcePosition position)
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
