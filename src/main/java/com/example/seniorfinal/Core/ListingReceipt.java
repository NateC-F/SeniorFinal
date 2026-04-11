package com.example.seniorfinal.Core;

import java.sql.Date;

public class ListingReceipt
{
    private String listingName;
    private String sellerName;
    private String buyerName;
    private Date saleDate;
    private int salePrice;
    private int quantity;
    private String town;
    private String state;
    private String location;
    private double latitude;
    private double longitude;

    public ListingReceipt(String listingName, Date saleDate, int salePrice, double longitude, double latitude, String state, String town, int quantity, String sellerName, String buyerName)
    {
        this.listingName = listingName;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
        this.town = town;
        this.quantity = quantity;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        location = town + ", " + state;
    }

    public String getListingName()
    {
        return listingName;
    }

    public void setListingName(String listingName)
    {
        this.listingName = listingName;
    }

    public Date getSaleDate()
    {
        return saleDate;
    }

    public void setSaleDate(Date saleDate)
    {
        this.saleDate = saleDate;
    }

    public int getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(int salePrice)
    {
        this.salePrice = salePrice;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getTown()
    {
        return town;
    }

    public void setTown(String town)
    {
        this.town = town;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getSellerName()
    {
        return sellerName;
    }

    public void setSellerName(String sellerName)
    {
        this.sellerName = sellerName;
    }

    public String getBuyerName()
    {
        return buyerName;
    }

    public void setBuyerName(String buyerName)
    {
        this.buyerName = buyerName;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}
