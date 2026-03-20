package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Category;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.Location;
import com.example.seniorfinal.Model.DAO.CategoryDAO;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListingCreateController implements Initializable
{
    @FXML
    ComboBox<Category> categoryBox;
    @FXML
    ComboBox<String> stateBox;
    @FXML
    TextField nameBox;
    @FXML
    TextField townBox;
    @FXML
    TextField streetBox;
    @FXML
    TextArea descriptionBox;
    @FXML
    TextField priceBox;
    @FXML
    ComboBox<Integer> quantityBox;
    @FXML
    DatePicker dateBox;
    @FXML
    Text errorText;


    private final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";
    private String address; // Street,Town,State
    private double longitude;
    private double latitude;



    public void convertAddrToLongAndLat() throws IOException, InterruptedException
    {
        finalizeAddr();
        String urlAddress = java.net.URLEncoder.encode(address, "UTF-8");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlAddress + "&key=" + APIKEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        if ("OK".equals(json.getString("status")))
        {
            JSONObject location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            latitude = location.getDouble("lat");
            longitude = location.getDouble("lng");
        }
        else
        {
            System.out.println("Geocoding failed: " + json.getString("status"));
            latitude = 0;
            longitude = 0;
        }

    }
    //=============================================================================================================
    public void finalizeAddr()
    {
        address = streetBox.getText()+","+townBox.getText()+","+stateBox.getValue();
    }
    //=============================================================================================================
    public String validateListing()
    {
     if (nameBox.getText().isEmpty())
         return "no_name";
     if (nameBox.getText().length()>50)
         return "name_too_long";
     if (descriptionBox.getText().isEmpty())
         return "no_description";
     if (descriptionBox.getText().length()>1000)
         return "description_too_long";
     if (dateBox.getValue()==null)
         return "no_ending_date";
     if (dateBox.getValue().isBefore(LocalDate.now().plusDays(3)))
         return "invalid_ending_date";
     try {
         int price = Integer.parseInt(priceBox.getText());
         if (price<=0)
            return "invalid_price";}
     catch(Exception e)
     {return "invalid_price";}
     if (quantityBox.getValue()==null)
        return "invalid_quantity";
     if (townBox.getText().isEmpty())
         return "no_town";
     if (streetBox.getText().isEmpty())
         return "no_street";
     if (categoryBox.getValue()==null)
         return "no_category";
     if (stateBox.getValue()==null)
         return "no_state";
     try{convertAddrToLongAndLat();}
     catch (Exception e)
        {return "invalid_location";}

     return "valid";
    }
    //=============================================================================================================
    @FXML
    public void createListing() throws IOException, InterruptedException
    {
        switch (validateListing())
        {
            case "no_name":
                errorText.setVisible(true);
                errorText.setText("Name Cannot Be Empty Enter Name");
                break;
            case "name_too_long":
                errorText.setVisible(true);
                errorText.setText("Name Cannot Be More Than 50 Characters");
                break;
            case "no_description":
                errorText.setVisible(true);
                errorText.setText("Description Cannot Be Empty Enter Description");
                break;
            case "description_too_long":
                errorText.setVisible(true);
                errorText.setText("Name Cannot Be More Than 1000 Characters");
                break;
            case "no_ending_date":
                errorText.setVisible(true);
                errorText.setText("You Must Pick An Ending Date");
                break;
            case "invalid_ending_date":
                errorText.setVisible(true);
                errorText.setText("You Must Pick A Valid Ending Date (3 Days From Today At Least)");
                break;
            case "invalid_price":
                errorText.setVisible(true);
                errorText.setText("Prices Must Be Positive Integer Values Only ie. 50, or 23");
                break;
            case "invalid_quantity":
                errorText.setVisible(true);
                errorText.setText("You Must Select A Quantity To Sell");
                break;
            case "no_town":
                errorText.setVisible(true);
                errorText.setText("You Must Enter A Town Name");
                break;
            case "no_street":
                errorText.setVisible(true);
                errorText.setText("You Must Enter A Street Address");
                break;
            case "no_state":
                errorText.setVisible(true);
                errorText.setText("You Must Select A State");
                break;
            case "no_category":
                errorText.setVisible(true);
                errorText.setText("You Must Select A Category");
                break;
            case "invalid_location":
                errorText.setVisible(true);
                errorText.setText("The Location You Entered Wasn't Valid");
                break;
            case "valid":
                new ListingDAO().CreateListing(nameBox.getText(),descriptionBox.getText(),Integer.parseInt(priceBox.getText()),
                        Date.valueOf(dateBox.getValue()),stateBox.getValue(),townBox.getText(),longitude,latitude,
                        quantityBox.getValue(),categoryBox.getValue().getId());
                SceneManager.switchTo(SceneID.MainScreen);
                break;
            default:
                errorText.setVisible(true);
                errorText.setText("There Was An Issue Creating Your Listing Try Again Later");
                break;
        }
    }
    //=============================================================================================================
    @FXML
    public void goBack()
    {SceneManager.switchTo(SceneID.MainScreen);}
    //=============================================================================================================



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        categoryBox.getItems().addAll(new CategoryDAO().pullCategories());
        stateBox.getItems().addAll(new Location().setStates());
        quantityBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);

        priceBox.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {priceBox.setText(oldText);}});
    }
}
