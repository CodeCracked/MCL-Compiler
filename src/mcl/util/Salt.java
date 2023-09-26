package mcl.util;

import java.util.Random;

public final class Salt
{
    private static final Random RANDOM = new Random();
    private static final String SALT_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    
    public static String newSalt(int length)
    {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < length; i++) salt.append(SALT_CHARS.charAt(RANDOM.nextInt(SALT_CHARS.length())));
        return salt.toString();
    }
}
