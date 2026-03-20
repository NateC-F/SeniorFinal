package com.example.seniorfinal.Model.DAO;

import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ListingDAO
{
    private String sqlCode;
    private PreparedStatement statement;

//=============================================================================================================
    public void CreateListing(String listingName, String listingDescription, int listingPrice, Date endingDate,
                              String state, String town, double longitude, double latitude, int listingQuantity, int categoryID)
    {
        sqlCode = "CALL create_listing(?,?,?,?,?,?,?,?,?,?,?)";

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
//      11)IN listing_input_categoryID INT)

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
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
