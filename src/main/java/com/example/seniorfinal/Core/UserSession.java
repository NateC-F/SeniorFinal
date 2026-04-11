package com.example.seniorfinal.Core;

import com.example.seniorfinal.Model.DAO.ListingDAO;

import java.util.ArrayList;

public class UserSession
{
    private static UserSession session;
    private UserAccount activeUser;
    private Listing userActiveViewListing;
    private Cart userCart;

    private UserSession()
    {

    }

    public static UserSession getSession()
    {
        if (session == null)
            session = new UserSession();

        return session;
    }

    public void setActiveUser(UserAccount user)
    {
        this.activeUser = user;
        userCart = new Cart();
    }

    public UserAccount getActiveUser()
    {
        return activeUser;
    }

    public void logout()
    {
        activeUser = null;
        userActiveViewListing = null;
        userCart = null;
    }

    public void setActiveViewListing(Listing listing)
    {
        userActiveViewListing = listing;
    }

    public Listing getActiveViewListing()
    {
        return userActiveViewListing;
    }


    public Cart getUserCart()
    {
        return userCart;
    }

    public void setUserCart(Cart userCart)
    {
        this.userCart = userCart;
    }

    public ArrayList<ListingReceipt> getOrderHistory()
    {
        return new ListingDAO().getOrderHistory(getActiveUser().getAccountID());
    }

    public ArrayList<ListingReceipt> getSaleHistory()
    {
        return new ListingDAO().getSaleHistory(getActiveUser().getAccountID());
    }


}