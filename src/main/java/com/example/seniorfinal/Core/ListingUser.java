package com.example.seniorfinal.Core;

public class ListingUser implements Account
{
    private String accountName;
    private int accountID;

    public ListingUser()
    {

    }

    @Override
    public void setAccountID(int id)
    {
        if (!(id<0))
            accountID = id;
    }

    @Override
    public void setAccountName(String name)
    {
        if (!name.isEmpty())
            accountName=name;
    }

    @Override
    public String getAccountName()
    {
        return accountName;
    }

    @Override
    public int getAccountID()
    {
        return accountID;
    }

}
