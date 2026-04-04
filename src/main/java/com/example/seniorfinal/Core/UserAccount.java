package com.example.seniorfinal.Core;

import java.time.LocalDate;

public class UserAccount implements Account
{
    private String accountName;
    private int accountID;
    private LocalDate creationDate;

    public UserAccount()
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

    @Override
    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    @Override
    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }
}
