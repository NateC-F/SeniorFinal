package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Category;
import com.example.seniorfinal.Core.ImageBlob;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.Location;
import com.example.seniorfinal.Model.DAO.CategoryDAO;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
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
    @FXML
    ImageView picturePreview;



    private final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";
    private String address; // Street,Town,State
    private double longitude;
    private double latitude;
    private String town ="";
    private String state="";
    private Blob image;



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
        convertLongAndLatToAddr();
    }
    //=============================================================================================================
    public void convertLongAndLatToAddr()
    {
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + APIKEY;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP error: " + responseCode);
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");

            if (results.length() > 0) {
                JSONArray components = results.getJSONObject(0).getJSONArray("address_components");

                for (int i = 0; i < components.length(); i++) {
                    JSONObject comp = components.getJSONObject(i);
                    JSONArray types = comp.getJSONArray("types");

                    for (int j = 0; j < types.length(); j++) {
                        String type = types.getString(j);
                        if (type.equals("locality")) {
                            town = comp.getString("long_name");
                            break; // stop checking other types for this component
                        }
                        else if (type.equals("administrative_area_level_1")) {
                            String fullState = comp.getString("long_name");
                            String code = stateNameToCode(fullState);
                            if (code != null) {
                                state = code;
                            }
                            else state = "N/A";
                            break; // stop checking other types for this component
                        }
                    }
                }

            } else {
                System.out.println("No results found for these coordinates.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //=============================================================================================================
    private String stateNameToCode(String fullState) {
        switch (fullState) {
            case "Alabama": return "AL";
            case "Alaska": return "AK";
            case "Arizona": return "AZ";
            case "Arkansas": return "AR";
            case "California": return "CA";
            case "Colorado": return "CO";
            case "Connecticut": return "CT";
            case "Delaware": return "DE";
            case "Florida": return "FL";
            case "Georgia": return "GA";
            case "Hawaii": return "HI";
            case "Idaho": return "ID";
            case "Illinois": return "IL";
            case "Indiana": return "IN";
            case "Iowa": return "IA";
            case "Kansas": return "KS";
            case "Kentucky": return "KY";
            case "Louisiana": return "LA";
            case "Maine": return "ME";
            case "Maryland": return "MD";
            case "Massachusetts": return "MA";
            case "Michigan": return "MI";
            case "Minnesota": return "MN";
            case "Mississippi": return "MS";
            case "Missouri": return "MO";
            case "Montana": return "MT";
            case "Nebraska": return "NE";
            case "Nevada": return "NV";
            case "New Hampshire": return "NH";
            case "New Jersey": return "NJ";
            case "New Mexico": return "NM";
            case "New York": return "NY";
            case "North Carolina": return "NC";
            case "North Dakota": return "ND";
            case "Ohio": return "OH";
            case "Oklahoma": return "OK";
            case "Oregon": return "OR";
            case "Pennsylvania": return "PA";
            case "Rhode Island": return "RI";
            case "South Carolina": return "SC";
            case "South Dakota": return "SD";
            case "Tennessee": return "TN";
            case "Texas": return "TX";
            case "Utah": return "UT";
            case "Vermont": return "VT";
            case "Virginia": return "VA";
            case "Washington": return "WA";
            case "West Virginia": return "WV";
            case "Wisconsin": return "WI";
            case "Wyoming": return "WY";
            default: return "N/A";
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
     if (nameBox.getText().trim().isEmpty())
         return "no_name";
     if (nameBox.getText().length()>50)
         return "name_too_long";
     if (descriptionBox.getText().trim().isEmpty())
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
     if (townBox.getText().trim().isEmpty())
         return "no_town";
     if (streetBox.getText().trim().isEmpty())
         return "no_street";
     if (categoryBox.getValue()==null)
         return "no_category";
     if (stateBox.getValue()==null)
         return "no_state";
     try{convertAddrToLongAndLat();}
     catch (Exception e)
        {return "invalid_location";}
     if (state.equals("N/A"))
         return "invalid_location";
     if (longitude == 0 && latitude == 0)
         return "invalid_location";
     if (picturePreview.getImage() == null || picturePreview.getImage().isError())
         return "null_image";
     return "valid";
    }
    //=============================================================================================================
    @FXML
    public void createListing() throws IOException, InterruptedException, SQLException
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
                errorText.setText("Descriptions Can Only Be 1000 Characters, You Have:" + descriptionBox.getText().length());
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
            case "null_image":
                errorText.setVisible(true);
                errorText.setText("You Must Upload A Picture Of Your Item");
                break;
            case "valid":
                image = ImageBlob.imageToBlob(picturePreview.getImage(), JDBC.getConnection());
                if(new ListingDAO().CreateListing(nameBox.getText(),descriptionBox.getText(),Integer.parseInt(priceBox.getText()),
                        Date.valueOf(dateBox.getValue()),state,town,longitude,latitude,
                        quantityBox.getValue(),categoryBox.getValue().getId(),image))
                    SceneManager.switchTo(SceneID.MainScreen);
                else
                {
                    errorText.setVisible(true);
                    errorText.setText("There Was An Issue Creating Your Listing Try Again Later");
                }
                break;
            default:
                errorText.setVisible(true);
                errorText.setText("There Was An Issue Creating Your Listing Try Again Later");
                break;
        }
    }
    //=============================================================================================================
    @FXML
    public void uploadImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Your Image");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        String userHome = System.getProperty("user.home");
        File defaultDir = new File(userHome, "Pictures");
        if (defaultDir.exists() && defaultDir.isDirectory()) {
            fileChooser.setInitialDirectory(defaultDir);
        }

        File selectedFile = fileChooser.showOpenDialog(picturePreview.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                if (image.isError()) {
                    System.out.println("Error loading image: " + image.getException());
                    errorText.setText("Unsupported or corrupted image");
                    errorText.setVisible(true);
                    return;
                }
                picturePreview.setImage(image);
                errorText.setVisible(false);
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                errorText.setText("Image Not Available Try Again");
                errorText.setVisible(true);
            }
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
