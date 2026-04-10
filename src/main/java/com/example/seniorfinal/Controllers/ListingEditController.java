package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ListingEditController implements Initializable
{
    @FXML
    TextField nameBox;
    @FXML
    TextField priceBox;
    @FXML
    TextField quantityBox;
    @FXML
    TextArea descriptionBox;
    @FXML
    Text errorText;
    private String sqlCode;
    private Runnable onUpdate;

    //=============================================================================================================
    public void goBack()
    {
        SceneManager.switchTo(SceneID.ProfileScreen);
    }
    //=============================================================================================================
    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }
    //=============================================================================================================
    public String validateChanges()
    {
        if (!nameBox.getText().isEmpty() && nameBox.getText().length() > 50)
            return "name_too_long";
        if (!descriptionBox.getText().isEmpty() && descriptionBox.getText().length() > 1000)
            return "description_too_long";
        if (!priceBox.getText().isEmpty() && Integer.parseInt(priceBox.getText())<=0)
            return "invalid_price";
        if (!quantityBox.getText().isEmpty() && ( Integer.parseInt(quantityBox.getText())<=0 || Integer.parseInt(quantityBox.getText())>20 ))
            return "invalid_quantity";

        return "valid";
    }
    //=============================================================================================================
    public void makeChanges()
    {
        String name;
        String description;
        int price;
        int quantity;

        switch(validateChanges())
        {
            case "valid":
                Listing listing = UserSession.getSession().getActiveViewListing();
                sqlCode = "UPDATE listing " +
                        "SET listing_name = ?, listing_description = ?, listing_price = ?, listing_quantity = ? " +
                        "WHERE listing_id = ?";
                if (nameBox.getText().trim().isEmpty())
                    name = listing.getName();
                else name = nameBox.getText();
                if (priceBox.getText().trim().isEmpty())
                    description = listing.getDescription();
                else description = descriptionBox.getText();
                if (priceBox.getText().isEmpty())
                    price = listing.getPrice();
                else price = Integer.parseInt(priceBox.getText());
                if (quantityBox.getText().isEmpty())
                    quantity = listing.getQuantity();
                else quantity = Integer.parseInt(quantityBox.getText());

                try (Connection connection = JDBC.getConnection())
                {
                    PreparedStatement statement = connection.prepareStatement(sqlCode);
                    statement.setString(1, name);
                    statement.setString(2, description);
                    statement.setInt(3, price);
                    statement.setInt(4, quantity);
                    statement.setInt(5, listing.getId());
                    statement.executeUpdate();
                    onUpdate.run();
                    ((Stage) nameBox.getScene().getWindow()).close();
                } catch (Exception e)
                {
                    System.out.println("Error Editing ");
                }
                break;
            case "name_too_long":
                errorText.setVisible(true);
                errorText.setText("Listing Names Can Only Be 50 Characters");
                break;
            case "description_too_long":
                errorText.setVisible(true);
                errorText.setText("Descriptions Can Only Be 1000 Characters, You Have: "+ descriptionBox.getText().length());
                break;
            case "invalid_price":
                errorText.setVisible(true);
                errorText.setText("Prices Must Be Positive And Whole Numbers");
                break;
            case "invalid_quantity":
                errorText.setVisible(true);
                errorText.setText("Quantities Must Fall Between 1-20");
                break;

        }


    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        priceBox.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {priceBox.setText(oldText);}});
        quantityBox.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {priceBox.setText(oldText);}});
    }
}
