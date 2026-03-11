package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private Text welcomeText;


    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        welcomeText.setText("Welcome to MarketPlace "+UserSession.getSession().getActiveUser().getAccountName());
    }
    //=============================================================================================================
    @FXML
    public void logoutOnClick()
    {
        UserSession.getSession().logout();
        SceneManager.switchTo(SceneID.LoginScreen);
    }
//=============================================================================================================
    @FXML
    public void newListing()
    {
        SceneManager.switchTo(SceneID.ListingCreateScreen);
    }
    //=============================================================================================================
    @FXML
    public void openSelfProfile()
    {
        SceneManager.switchTo(SceneID.ProfileScreen);
    }

}