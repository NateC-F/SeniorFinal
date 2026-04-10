package com.example.seniorfinal.Core;

import java.sql.Date;

public class ListingReceipt
{
    private String name;
    private Date endTime;
    private int salePrice;
    private double longitude;
    private double latitude;
    private String state;
    private String town;
    private int quantity;

    public ListingReceipt(String name, Date endTime, int salePrice, double longitude, double latitude, String state, String town, int quantity)
    {
        this.name = name;
        this.endTime = endTime;
        this.salePrice = salePrice;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.town = town;
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public int getSalePrice()
    {
        return salePrice;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public String getState()
    {
        return state;
    }

    public String getTown()
    {
        return town;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
