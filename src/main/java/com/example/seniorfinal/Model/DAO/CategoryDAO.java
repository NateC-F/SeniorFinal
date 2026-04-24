package com.example.seniorfinal.Model.DAO;

import com.example.seniorfinal.Core.Category;
import com.example.seniorfinal.Utilities.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryDAO
{
    String sqlCode;
    PreparedStatement statement;

//=============================================================================================================
    public ArrayList<Category> pullCategories()
    {
        sqlCode = "Select * From item_category";
        ArrayList<Category> categories = new ArrayList<>();

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                categories.add(new Category(id));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return categories;
    }
    //=============================================================================================================
    public int getCategory(int listingID)
    {
        sqlCode = "SELECT category_id FROM listing_category  WHERE listing_id = ? LIMIT 1";
        int categoryID = -1;
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setInt(1,listingID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                categoryID = resultSet.getInt("category_id");
        }
        catch (Exception e)
        {

        }
        return categoryID;
    }
}

