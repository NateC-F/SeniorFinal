package com.example.seniorfinal.Core;

import java.time.LocalDate;

public interface Account
{
    public void setAccountID(int id);

    public void setAccountName(String name);

    public String getAccountName();

    public int getAccountID();

    public void setCreationDate(LocalDate creationDate);

    public LocalDate getCreationDate();
}
