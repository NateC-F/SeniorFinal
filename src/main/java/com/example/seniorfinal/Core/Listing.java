package com.example.seniorfinal.Core;

import java.sql.Date;
import java.time.LocalDate;

public class Listing
{
    private String name;
    private String description;
    private Boolean active;
    private Date startTime;
    private Date endTime;
    private int price;
    private int ownerID;
    private String pickUpLocation;
    private double longitude;
    private double latitude;
    private String url;

    public Listing(String name, String description, Date endTime, int price, String pickUpLocation, double longitude, double latitude)
    {
        this.name=name;
        this.description=description;
        active = true;
        startTime = Date.valueOf(LocalDate.now());
        this.endTime = endTime;
        this.price = price;
        ownerID = UserSession.getSession().getActiveUser().getAccountID();
        this.pickUpLocation = pickUpLocation;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public String getUrl()
    {
        return getClass().getResource("/map.html").toExternalForm() + "?latitude=" + latitude + "&longitude=" + longitude;
    }
}
