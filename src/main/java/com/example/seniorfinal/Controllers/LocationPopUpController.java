package com.example.seniorfinal.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.util.ResourceBundle;

public class LocationPopUpController
{
    @FXML
    private ImageView itemMap;
    private final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";

    //=============================================================================================================
    @FXML
    public void loadMap(double longitude, double latitude)
    {
        int zoomLevel = 15;
        String url = String.format("https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=%d&size=600x400&markers=color:red|%f,%f&key=%s",
                                latitude, longitude, zoomLevel, latitude, longitude, URLEncoder.encode(APIKEY, StandardCharsets.UTF_8));

        itemMap.setImage(new Image(url));
    }
    //=============================================================================================================
    public void setCoordinates(double[] address)
    {

        loadMap(address[1], address[0]);
    }
}
