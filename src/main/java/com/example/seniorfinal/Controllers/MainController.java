package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private Text welcomeText;
    @FXML
    ListView<Listing> listingHolder;


    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        welcomeText.setText("Welcome to MarketPlace "+UserSession.getSession().getActiveUser().getAccountName());

        listingHolder.setCellFactory(lv -> new ListCell<>() {

            private final Label nameLabel = new Label();
            private final Label priceLabel = new Label();
            private final Button button = new Button("View Listing");

            private final HBox layout = new HBox(10, nameLabel, priceLabel, button);

            {
                layout.setStyle("-fx-alignment: center-left; -fx-padding: 5;");

                // Button action
                button.setOnAction(e -> {
                    Listing listing = getItem();
                    UserSession.getSession().setActiveViewListing(listing);
                    SceneManager.switchTo(SceneID.ListingViewScreen);
                });
            }

            @Override
            protected void updateItem(Listing listing, boolean empty) {
                super.updateItem(listing, empty);

                if (empty || listing == null) {
                    setGraphic(null);
                } else {
                    nameLabel.setText(listing.getName());
                    priceLabel.setText("$" + listing.getPrice());

                    setGraphic(layout);
                }
            }
        });

        pullActiveListings();

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
    //=============================================================================================================
    public void pullActiveListings()
    {
        listingHolder.getItems().clear();
        listingHolder.getItems().addAll(new ListingDAO().getAllActiveListings());
    }
}