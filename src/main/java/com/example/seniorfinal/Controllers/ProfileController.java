package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable
{
    @FXML
    ListView<Listing> userListings;

    public void goBack()
    {
        SceneManager.switchTo(SceneID.MainScreen);
    }
    //=============================================================================================================
    public void openOrderHistory()
    {
        SceneManager.switchTo(SceneID.OrderHistoryScreen);
    }
    //=============================================================================================================
    public void pullOwnListings()
    {
        userListings.getItems().clear();
        userListings.getItems().addAll(new ListingDAO().getAllSelfListings());
    }
    //=============================================================================================================
    public  void openEditView()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/seniorfinal/View/editListing.fxml"));
            Parent root = loader.load();
            ListingEditController controller = loader.getController();
            controller.setOnUpdate(() -> pullOwnListings());
            Stage newStage = new Stage();
            newStage.setTitle("Edit Your Listing");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //=============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        userListings.setCellFactory(lv -> new ListCell<>() {

            private final Label nameLabel = new Label();
            private final Label statusLabel = new Label();
            private final Label description = new Label();
            private final ScrollPane descriptionScroll = new ScrollPane(description);
            private final Label price = new Label();
            private final Label quantity = new Label();
            private final Button editButton = new Button("Edit Listing");
            private final Button deleteButton = new Button("Delete Listing");

            private final HBox layout = new HBox(10, nameLabel, descriptionScroll, price, quantity, statusLabel, editButton, deleteButton);

            {
                layout.setStyle("-fx-alignment: center-left; -fx-padding: 5;");
                description.setWrapText(true);
                descriptionScroll.setPrefHeight(100);
                descriptionScroll.setPrefWidth(200);
                descriptionScroll.setFitToWidth(true);
                descriptionScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                descriptionScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                editButton.setOnAction(e -> {
                    Listing listing = getItem();
                    UserSession.getSession().setActiveViewListing(listing);
                    openEditView();
                });
                deleteButton.setOnAction(e -> {
                    Listing listing = getItem();
                    new ListingDAO().deleteListing(listing.getId());
                });
            }

            @Override
            protected void updateItem(Listing listing, boolean empty) {
                super.updateItem(listing, empty);

                if (empty || listing == null) {
                    setGraphic(null);
                } else {
                    nameLabel.setText(listing.getName());
                    if (listing.getActive())
                        statusLabel.setText("Active Listing");
                    else
                    {
                        statusLabel.setText("Item Sold");
                        editButton.setVisible(false);
                        deleteButton.setVisible(false);
                    }
                    description.setText(listing.getDescription());
                    price.setText(String.valueOf(listing.getPrice()));
                    quantity.setText(String.valueOf(listing.getQuantity()));

                    setGraphic(layout);
                }
            }
        });

        pullOwnListings();
    }

}
