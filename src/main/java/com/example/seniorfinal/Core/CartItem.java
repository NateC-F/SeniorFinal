package com.example.seniorfinal.Core;


public class CartItem
{
    private Listing listing;
    private int quantity;
    private int total;
    private int maxQuantity;

    public CartItem(Listing listing, int quantity, int maxQuantity)
    {
        this.listing = listing;
        this.quantity = quantity;
        total = listing.getPrice()*quantity;
        this.maxQuantity = maxQuantity;
    }

    public Listing getListing()
    {
        return listing;
    }

    public void setListing(Listing listing)
    {
        this.listing = listing;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
        total = this.quantity* listing.getPrice();
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getMaxQuantity()
    {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity)
    {
        this.maxQuantity = maxQuantity;
    }
}
