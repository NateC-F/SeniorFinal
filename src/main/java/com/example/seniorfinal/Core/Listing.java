package com.example.seniorfinal.Core;

import javafx.scene.image.Image;

import java.sql.Blob;
import java.sql.Date;

public class Listing
{
    private int id;
    private String name;
    private String description;
    private Boolean active;
    private Date startTime;
    private Date endTime;
    private int price;
    private int ownerID;
    private String state;
    private String town;
    private double longitude;
    private double latitude;
    private int quantity;
    private Blob listing_image;

    public Listing(int id,String name, String description, boolean active, Date startTime, Date endTime, int price,int ownerID, String town, String state, double longitude, double latitude, int quantity, Blob listing_image)
    {
        this.id = id;
        this.name=name;
        this.description=description;
        this.active = active;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.ownerID = ownerID;
        this.state = state;
        this.town = town;
        this.longitude = longitude;
        this.latitude = latitude;
        this.quantity = quantity;
        this.listing_image = listing_image;
    }
    public Listing(){}

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Boolean getActive()
    {
        return active;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public int getPrice()
    {
        return price;
    }

    public int getOwnerID()
    {
        return ownerID;
    }

    public String getState()
    {
        return state;
    }

    public String getTown()
    {
        return town;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public Blob getListing_image()
    {
        return listing_image;
    }

    public void setListing_image(Blob listing_image)
    {
        this.listing_image = listing_image;
    }

    public void setPrice(int i)
    {
        price = i;
    }

    public void setQuantity(int i)
    {
        quantity = i;
    }
}
