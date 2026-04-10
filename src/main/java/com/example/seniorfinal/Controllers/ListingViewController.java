package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.CartItem;
import com.example.seniorfinal.Core.ImageBlob;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;

import java.util.ResourceBundle;

public class ListingViewController implements Initializable
{
    final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";

    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemName;
    @FXML
    private Label itemDescription;
    @FXML
    private Label itemLocation;
    @FXML
    private Label itemPrice;
    @FXML
    private ComboBox<Integer>quantityBox;
    @FXML
    private Text errorText;


    //=============================================================================================================
//    @FXML
//    public void loadMap()
//    {
//
//        double latitude = UserSession.getSession().getActiveViewListing().getLatitude();
//        double longitude = UserSession.getSession().getActiveViewListing().getLongitude();
//        int zoomLevel = 15;
//        String url = String.format("https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=%d&size=600x400&markers=color:red|%f,%f&key=%s",
//                                latitude, longitude, zoomLevel, latitude, longitude, URLEncoder.encode(APIKEY, StandardCharsets.UTF_8));
//
//        itemMap.setImage(new Image(url));
//
//    }
    //=============================================================================================================
    @FXML
    public void goBack()
    {
        UserSession.getSession().setActiveViewListing(null);
        SceneManager.switchTo(SceneID.MainScreen);
    }
    //=============================================================================================================
    @FXML
    public void addToCart()
    {
        if (quantityBox.getValue() != null)
        {
            Listing listing = UserSession.getSession().getActiveViewListing();
            UserSession.getSession().getUserCart().addToCart(new CartItem(listing, quantityBox.getValue(), listing.getQuantity()));

            listing.setQuantity(listing.getQuantity()- quantityBox.getValue());
        }
        else
        {
            errorText.setVisible(true);
            errorText.setText("You Must Select A Purchase Quantity");
        }
        goBack();
    }
    //=============================================================================================================
    public void setUpListing()
    {
        Listing listing = UserSession.getSession().getActiveViewListing();
        for (int i=1; i<=listing.getQuantity();i++)
            quantityBox.getItems().add(i);

        itemName.setText(listing.getName());
        itemDescription.setText(listing.getDescription());
        itemLocation.setText("Location: " + listing.getTown()+", "+listing.getState());
        itemPrice.setText("Price: $" + listing.getPrice());
        itemImage.setImage(ImageBlob.blobToImage(listing.getListing_image()));
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
       // loadMap();
        setUpListing();

    }
}
