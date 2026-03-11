package com.example.seniorfinal.Utilities;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash
{
    public PasswordHash()
    {}

    public String hashString(String string)
    {
        return BCrypt.hashpw(string,BCrypt.gensalt());
    }
    public boolean verifyPassword(String password, String hash)
    {
        return BCrypt.checkpw(password, hash);
    }
}
