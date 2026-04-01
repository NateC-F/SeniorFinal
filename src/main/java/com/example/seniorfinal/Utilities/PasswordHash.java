package com.example.seniorfinal.Utilities;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash
{
    public PasswordHash()
    {}

    public static String hashString(String string)
    {
        return BCrypt.hashpw(string,BCrypt.gensalt());
    }
    public static boolean verifyHash(String string, String hash)
    {
        return BCrypt.checkpw(string, hash);
    }

}
