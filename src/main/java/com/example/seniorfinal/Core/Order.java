package com.example.seniorfinal.Core;

import java.time.LocalDate;

public class Order
{
    private ListingReceipt listingReceipt;
    private String seller;
    private String name;
    private LocalDate date;
    private int price;
    private int amount;
    private String location;



    public Order(ListingReceipt listingReceipt, String seller)
    {
     this.listingReceipt = listingReceipt;
     this.seller = seller;
     setName();
     setDate();
     setPrice();
     setAmount();
     setLocation();
    }
    public void setName()
    {
        name = listingReceipt.getName();
    }
    public void setDate()
    {
        date = listingReceipt.getEndTime().toLocalDate();
    }
    public void setPrice()
    {
        price = listingReceipt.getSalePrice()* listingReceipt.getQuantity();
    }
    public void setAmount()
    {
        amount = listingReceipt.getQuantity();
    }
    public void setLocation()
    {
        location = listingReceipt.getTown()+", "+listingReceipt.getState();
    }
    public ListingReceipt getListingReceipt()
    {
        return listingReceipt;
    }

    public void setListing(ListingReceipt listingReceipt)
    {
        this.listingReceipt = listingReceipt;
    }

    public String getSeller()
    {
        return seller;
    }

    public void setSeller(String seller)
    {
        this.seller = seller;
    }

    public String getName()
    {
        return name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public int getPrice()
    {
        return price;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getLocation()
    {
        return location;
    }
}
