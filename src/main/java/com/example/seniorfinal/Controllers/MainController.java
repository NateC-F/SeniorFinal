package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.ImageBlob;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
        System.out.println("Menu + " + UserSession.getSession().getUserCart().getItems().size());
        welcomeText.setText("Welcome to MarketPlace "+UserSession.getSession().getActiveUser().getAccountName());

        listingHolder.setCellFactory(lv -> new ListCell<>() {

            private final ImageView imageView = new ImageView();
            private final Label name = new Label();
            private final Label description = new Label();
            private final ScrollPane descriptionScroll = new ScrollPane(description);
            private final Label price = new Label();
            private final Label quantity = new Label();
            private final Label location = new Label();
            private final Button viewButton = new Button("View Listing");
            private final Region spacer = new Region();
            private final VBox textBox = new VBox(5, name, price, quantity,location);
            private final HBox topRow = new HBox(15, imageView, textBox, descriptionScroll, spacer, viewButton);
            private final VBox layout = new VBox(topRow);
            {

                layout.setStyle("-fx-padding: 10; -fx-border-color: #000; -fx-border-width: 0 0 1 0;" );
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                description.setWrapText(true);
                descriptionScroll.setPrefHeight(100);
                descriptionScroll.setPrefWidth(400);
                descriptionScroll.setFitToWidth(true);
                descriptionScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                descriptionScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                name.setStyle("-fx-font-size: 24px;");
                HBox.setHgrow(spacer,Priority.ALWAYS);
                description.setStyle("-fx-font-size: 16px;");
                price.setStyle("-fx-font-size: 16px;");
                quantity.setStyle("-fx-font-size: 16px;");
                viewButton.setPrefSize(100,75);
                viewButton.setStyle("-fx-font-size: 14px;");
                location.setStyle("-fx-font-size: 16px;");

                viewButton.setOnAction(e -> {
                    Listing listing = getItem();
                    if (listing != null) {
                        UserSession.getSession().setActiveViewListing(listing);
                        SceneManager.switchTo(SceneID.ListingViewScreen);
                    }
                });
            }

            @Override
            protected void updateItem(Listing listing, boolean empty) {
                super.updateItem(listing, empty);
                if (empty || listing == null) {
                    setGraphic(null);
                } else {
                    boolean inCart = UserSession.getSession().getUserCart().isListingInCart(listing);

                    if (inCart) {
                        // Hide the listing completely if it's in the cart
                        setGraphic(null);
                        return;
                    }

                    // Update UI with remaining quantity
                    name.setText(listing.getName());
                    description.setText("Description: " + listing.getDescription());
                    price.setText("Price: $" + listing.getPrice());
                    quantity.setText("Available: " + listing.getQuantity());
                    location.setText("Item Location: " + listing.getTown() + ", " + listing.getState());
                    imageView.setImage(ImageBlob.blobToImage(listing.getListing_image()));

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
    public void openCheckOut()
    {
        SceneManager.switchTo(SceneID.CheckoutScreen);
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