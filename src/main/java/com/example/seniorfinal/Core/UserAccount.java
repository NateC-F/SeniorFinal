package com.example.seniorfinal.Core;

import java.time.LocalDate;

public class UserAccount
{
    private String accountName;
    private int accountID;
    private LocalDate creationDate;

    public UserAccount()
    {
    }


    public void setAccountID(int id)
    {
        if (!(id<0))
            accountID = id;
    }


    public void setAccountName(String name)
    {
        if (!name.isEmpty())
            accountName=name;
    }


    public String getAccountName()
    {
        return accountName;
    }


    public int getAccountID()
    {
        return accountID;
    }


    public LocalDate getCreationDate()
    {
        return creationDate;
    }


    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }


}
