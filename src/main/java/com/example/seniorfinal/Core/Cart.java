package com.example.seniorfinal.Core;

import java.util.ArrayList;

public class Cart
{
    private ArrayList<CartItem> listings;

    public Cart()
    {
        listings = new ArrayList<>();
    }

    public int getCartTotal()
    {
        int runningTotal=0;

        for (CartItem item: listings)
        {
            runningTotal+=item.getTotal();
        }
        return runningTotal;
    }

    public void addToCart(CartItem item)
    {
        listings.add(item);
        System.out.println("Item Added");
    }
    public void removeFromCart(CartItem item)
    {
        listings.remove(item);
        System.out.println("Item removed");
    }

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
