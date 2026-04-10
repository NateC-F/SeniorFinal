package com.example.seniorfinal.Model.DAO;

import com.example.seniorfinal.Core.*;
import com.example.seniorfinal.Utilities.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ListingDAO
{
    private String sqlCode;
    private PreparedStatement statement;

//=============================================================================================================
    public boolean CreateListing(String listingName, String listingDescription, int listingPrice, Date endingDate,
                              String state, String town, double longitude, double latitude, int listingQuantity, int categoryID, Blob listingImage)
    {
        sqlCode = "CALL create_listing(?,?,?,?,?,?,?,?,?,?,?,?)";

//      CREATE PROCEDURE create_listing(
//      1)IN listing_input_name VARCHAR(50),
//      2)IN listing_input_description VARCHAR(250),
//      3)IN listing_input_userID INT,
//      4)IN listing_input_price INT,
//      5)IN listing_input_endTime DATE,
//      6)IN listing_input_state VARCHAR(2),
//      7)IN listing_input_town VARCHAR(50),
//      8)IN listing_input_longitude DECIMAL(9,6),
//      9)IN listing_input_latitude DECIMAL(9,6),
//      10)IN listing_input_quantity INT,
//      11)IN listing_input_categoryID INT,
//      12)IN listing_input_image BLOB
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,listingName);
            statement.setString(2,listingDescription);
            statement.setInt(3, UserSession.getSession().getActiveUser().getAccountID());
            statement.setInt(4, listingPrice);
            statement.setDate(5,endingDate);
            statement.setString(6,state);
            statement.setString(7,town);
            statement.setDouble(8,longitude);
            statement.setDouble(9,latitude);
            statement.setInt(10, listingQuantity);
            statement.setInt(11, categoryID);
            statement.setBlob(12, listingImage);
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

        return true;
    }
    //=============================================================================================================
    public Listing getListing(int listingID)
    {
        sqlCode = "Select * From listing Where listing_id is ?";
        Listing listing = null;
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,String.valueOf(listingID));

            ResultSet rs = statement.executeQuery();

            int listing_id = rs.getInt("listing_id");
            String listing_name = rs.getString("listing_name");
            String listing_description = rs.getString("listing_description");
            Date start_date = rs.getDate("listing_start");
            Date end_date = rs.getDate("listing_end");
            int listing_price = rs.getInt("listing_price");
            double longitude = rs.getDouble("listing_longitude");
            double latitude = rs.getDouble("listing_latitude");
            String town_name = rs.getString("listing_town");
            String state = rs.getString("listing_state");
            int quantity = rs.getInt("listing_quantity");
            int seller_id = rs.getInt("user_id");
            Blob listingImage = rs.getBlob("listing_picture");
            boolean active = true;

            listing = new Listing(listing_id,listing_name,listing_description,active,start_date,end_date,listing_price,
                    seller_id,town_name,state,longitude,latitude, quantity,listingImage);
        }
        catch (Exception e)
        {

        }
        return listing;
    }
    //=============================================================================================================
    public ArrayList<Listing> getAllActiveListings()
    {
        sqlCode = "SELECT *  FROM listing WHERE listing_active is true AND user_id != ?;";
        ArrayList<Listing> listings = new ArrayList<>();
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setInt(1,UserSession.getSession().getActiveUser().getAccountID());
            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                int listing_id = rs.getInt("listing_id");
                String listing_name = rs.getString("listing_name");
                String listing_description = rs.getString("listing_description");
                Date start_date = rs.getDate("listing_start");
                Date end_date = rs.getDate("listing_end");
                int listing_price = rs.getInt("listing_price");
                double longitude = rs.getDouble("listing_longitude");
                double latitude = rs.getDouble("listing_latitude");
                String town_name = rs.getString("listing_town");
                String state = rs.getString("listing_state");
                int quantity = rs.getInt("listing_quantity");
                int seller_id = rs.getInt("user_id");
                Blob listingImage = rs.getBlob("listing_picture");
                boolean active =true;

                listings.add(new Listing(listing_id,listing_name,listing_description,active,start_date,end_date,listing_price,
                        seller_id,town_name,state,longitude,latitude, quantity,listingImage));
            }
        }
        catch (Exception e)
        {

        }
        return listings;
    }
    //=============================================================================================================
    public ArrayList<Listing> getAllSelfListings()
    {
        sqlCode = "SELECT *  FROM listing WHERE user_id = ?;";
        ArrayList<Listing> listings = new ArrayList<>();
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setInt(1,UserSession.getSession().getActiveUser().getAccountID());
            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                int listing_id = rs.getInt("listing_id");
                String listing_name = rs.getString("listing_name");
                String listing_description = rs.getString("listing_description");
                Date start_date = rs.getDate("listing_start");
                Date end_date = rs.getDate("listing_end");
                int listing_price = rs.getInt("listing_price");
                double longitude = rs.getDouble("listing_longitude");
                double latitude = rs.getDouble("listing_latitude");
                String town_name = rs.getString("listing_town");
                String state = rs.getString("listing_state");
                int quantity = rs.getInt("listing_quantity");
                int seller_id = rs.getInt("user_id");
                Blob listingImage = rs.getBlob("listing_picture");
                boolean active =rs.getBoolean("listing_active");

                listings.add(new Listing(listing_id,listing_name,listing_description,active,start_date,end_date,listing_price,
                        seller_id,town_name,state,longitude,latitude, quantity,listingImage));
            }
        }
        catch (Exception e)
        {

        }
        return listings;
    }
    //=============================================================================================================
    public void deleteListing(int listingID)
    {
        sqlCode = "CALL delete_listing("+ listingID + ")";
    }
    //=============================================================================================================
    public PurchaseResult purchaseCart(Cart cart)
    {
        // IN listing_input_id INT,
        // IN listing_buyer_id INT,
        // IN purchase_amount INT
        sqlCode = "CALL buy_listing(?, ?, ?)";

        PurchaseResult result = new PurchaseResult();

        for (CartItem item : new ArrayList<>(cart.getItems()))
        {
            try (Connection connection = JDBC.getConnection())
            {
                CallableStatement statement = connection.prepareCall(sqlCode);

                statement.setInt(1, item.getListing().getId());
                statement.setInt(2, UserSession.getSession().getActiveUser().getAccountID());
                statement.setInt(3, item.getQuantity());

                statement.execute();

                result.totalCharge += item.getTotal();
                cart.removeFromCart(item);
            }
            catch (SQLException e)
            {
                String msg = e.getMessage().toLowerCase();

                result.failedMessages.add(
                        item.getListing().getName() + ": " + msg
                );
            }
        }

        return result;
    }
    //=============================================================================================================
}
