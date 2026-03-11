package com.example.seniorfinal.Core;

import com.example.seniorfinal.Model.DAO.AccountDAO;

public class ActiveUser implements Account
{
    private String accountName;
    private int accountID;

    public ActiveUser()
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
