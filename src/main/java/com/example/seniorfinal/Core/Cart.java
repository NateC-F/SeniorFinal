package com.example.seniorfinal.Core;

import com.example.seniorfinal.Model.DAO.ListingDAO;

import java.util.ArrayList;

public class Cart
{
    private ArrayList<CartItem> listings;

    public Cart()
    {
        listings = new ArrayList<>();
    }
    //=============================================================================================================
    public int getCartTotal()
    {
        int runningTotal=0;

        for (CartItem item: listings)
        {
            runningTotal+=item.getTotal();
        }
        return runningTotal;
    }
    //=============================================================================================================
    public void addToCart(CartItem item)
    {
        listings.add(item);
        System.out.println("Item Added");
    }//=============================================================================================================
    public void removeFromCart(CartItem item)
    {
        listings.remove(item);
        System.out.println("Item removed");
    }//=============================================================================================================
    public void updateCart()
    {
        ArrayList<CartItem> tempList = new ArrayList<>(listings);
        ArrayList<CartItem> toRemove = new ArrayList<>();

        for (CartItem listing : tempList)
        {
            listing.setMaxQuantity(new ListingDAO().getListingQuantity(listing.getListing().getId()));

            if (listing.getMaxQuantity() == 0)
                toRemove.add(listing);
            else
                listing.setQuantity(listing.getMaxQuantity());
        }

        for (CartItem item : toRemove) {
            listings.remove(item);
        }
    }
    //=============================================================================================================

    public ArrayList<CartItem> getItems()
    {
        return listings;
    }

    public void updateQuantity(Listing listing, int newQuantity)
    {
        for (CartItem item : listings)
            if (listing.getId() == item.getListing().getId())
            {
                item.setQuantity(newQuantity);
                break;
            }
    }

    public boolean isListingInCart(Listing listing)
    {
        for (CartItem item : listings)
            if (listing.getId() == item.getListing().getId())
                return true;
        return false;
    }

    public void removeAllItemsFromCart()
    {
        listings.clear();
    }


}
