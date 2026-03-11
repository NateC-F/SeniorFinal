package com.example.seniorfinal.Core;

public class UserSession
{
    private static UserSession session;
    private Account activeUser;

    private UserSession()
    {

    }

    public static UserSession getSession()
    {
        if (session == null)
            session = new UserSession();

        return session;
    }

    public void setActiveUser(Account user)
    {
        this.activeUser = user;
    }

    public Account getActiveUser()
    {
        return activeUser;
    }

    public void logout()
    {
        activeUser = null;
    }


}