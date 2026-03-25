package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ListingViewController implements Initializable
{
    final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";

    @FXML
    private ImageView itemMap;

    //=============================================================================================================
    @FXML
    public void loadMap()
    {

        double latitude = UserSession.getSession().getActiveViewListing().getLatitude();
        double longitude = UserSession.getSession().getActiveViewListing().getLongitude();
        int zoomLevel = 15;
        String url = String.format("https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=%d&size=600x400&markers=color:red|%f,%f&key=%s",
                                latitude, longitude, zoomLevel, latitude, longitude, URLEncoder.encode(APIKEY, StandardCharsets.UTF_8));

        itemMap.setImage(new Image(url));

    }
    //=============================================================================================================
    @FXML
    public void goBack()
    {
        UserSession.getSession().setActiveViewListing(null);
        SceneManager.switchTo(SceneID.MainScreen);
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadMap();
    }
}
