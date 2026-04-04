package com.example.seniorfinal.Core;

public class UserSession
{
    private static UserSession session;
    private Account activeUser;
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

    public void setActiveUser(Account user)
    {
        this.activeUser = user;
        userCart = new Cart();
    }

    public Account getActiveUser()
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
}