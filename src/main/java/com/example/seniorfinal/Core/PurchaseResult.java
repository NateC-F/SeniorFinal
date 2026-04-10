package com.example.seniorfinal.Core;

import java.util.ArrayList;

public  class PurchaseResult
{
    private int totalCharge;
    private ArrayList<String> failedMessages = new ArrayList<>();

    public ArrayList<String> getFailedMessages()
    {
        return failedMessages;
    }

    public void addFailedMessages(String name, String error)
    {
       failedMessages.add(name + ": " + error);
    }

    public int getTotalCharge()
    {
        return totalCharge;
    }

    public void setTotalCharge(int totalCharge)
    {
        this.totalCharge = totalCharge;
    }
}
