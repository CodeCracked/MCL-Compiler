package compiler.core.lexer.builders;

import compiler.core.lexer.AbstractTokenBuilder;
import compiler.core.lexer.Lexer;
import compiler.core.lexer.Token;
import compiler.core.lexer.base.LiteralTokenType;
import compiler.core.source.SourcePosition;

public class NumberLiteralsTokenBuilder extends AbstractTokenBuilder
{
    private enum Base { BINARY, DECIMAL, OCTAL, HEX, UNKNOWN }
    
    private final Enum<?> integerType;
    private final Enum<?> decimalType;
    
    public NumberLiteralsTokenBuilder(Enum<?> integerType, Enum<?> decimalType)
    {
        this.integerType = integerType;
        this.decimalType = decimalType;
    }
    
    public static NumberLiteralsTokenBuilder normal() { return new NumberLiteralsTokenBuilder(LiteralTokenType.INTEGER, LiteralTokenType.DECIMAL); }
    
    @Override
    public Token tryBuild(Lexer lexer, SourcePosition position)
    {
        if (!Character.isDigit(position.getCharacter()) && position.getCharacter() != '.') return null;
        else
        {
            SourcePosition start = position.copy();
            
            // Get the literal's base
            Base base = getBase(position);
            if (base == Base.UNKNOWN) return null;
    
            // Build literal contents
            StringBuilder contents = new StringBuilder();
            boolean hasDecimal = false;
            while (position.valid())
            {
                char c = position.getCharacter();
                
                // Handle Decimal Point
                if (c == '.')
                {
                    if (hasDecimal || base != Base.DECIMAL) break;
                    hasDecimal = true;
                    contents.append(c);
                    position.advance();
                }
                
                // Handle Digit
                else if (isDigit(c, base))
                {
                    contents.append(c);
                    position.advance();
                }
                
                // Handle Other Character
                else if (contents.length() > 0) break;
                else return null;
            }
            
            // Parse literal contents
            SourcePosition end = position.copy(); end.retract();
            if (hasDecimal)
            {
                try
                {
                    float value = Float.parseFloat(contents.toString());
                    return new Token(decimalType, value, start, end);
                }
                catch (NumberFormatException e) { return null; }
            }
            else
            {
                try
                {
                    int value = switch (base)
                    {
                        case BINARY -> Integer.parseInt(contents.toString(), 2);
                        case OCTAL -> Integer.parseInt(contents.toString(), 8);
                        case HEX -> Integer.parseInt(contents.toString(), 16);
                        case DECIMAL -> Integer.parseInt(contents.toString(), 10);
                        case UNKNOWN -> throw new NumberFormatException("Unknown base!");
                    };
                    return new Token(integerType, value, start, end);
                }
                catch (NumberFormatException e) { return null; }
            }
        }
    }
    
    private Base getBase(SourcePosition position)
    {
        if (position.getCharacter() == '0')
        {
            position.advance();
            
            if (Character.isDigit(position.getCharacter())) return Base.OCTAL;
            else if (position.getCharacter() == 'b')
            {
                position.advance();
                return Base.BINARY;
            }
            else if (position.getCharacter() == 'x')
            {
                position.advance();
                return Base.HEX;
            }
            else
            {
                position.retract();
                return Base.DECIMAL;
            }
        }
        else return Base.DECIMAL;
    }
    private boolean isDigit(char c, Base base)
    {
        return switch (base)
        {
            case BINARY -> c == '0' || c == '1';
            case OCTAL -> "01234567".indexOf(c) >= 0;
            case DECIMAL -> "0123456789".indexOf(c) >= 0;
            case HEX -> "0123456789abcdefABCDEF".indexOf(c) >= 0;
            case UNKNOWN -> false;
        };
    }
}
