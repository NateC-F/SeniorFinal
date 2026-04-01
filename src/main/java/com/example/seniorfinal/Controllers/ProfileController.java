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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/seniorfinal/View/fxml/editListing.fxml"));
            Parent root = loader.load();
            ListingEditController controller = loader.getController();
            controller.setOnUpdate(() -> pullOwnListings());
            Stage newStage = new Stage();
            newStage.setTitle("Edit Your Listing");
            Scene scene = new Scene(root);
            String css = getClass().getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm();
            scene.getStylesheets().add(css);
            newStage.setScene(scene);
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

            private Text boldName = new Text("Name: ");
            private Text boldDesc = new Text("Description:\n");
            private Text boldQnt = new Text("Amount: ");
            private final Label nameLabel = new Label();
            private final Text description = new Text();
            private final Label quantity = new Label();

            TextFlow nameFlow = new TextFlow(boldName, nameLabel);
            TextFlow descFlow = new TextFlow(boldDesc, description);
            TextFlow qntFlow = new TextFlow(boldQnt, quantity);

            private final ScrollPane descriptionScroll = new ScrollPane();
            private final Label price = new Label();
            private final Label statusLabel = new Label();
            private final Button editButton = new Button("Edit Listing");
            private final Button deleteButton = new Button("Delete Listing");

            private final HBox layout = new HBox(10, nameFlow, descriptionScroll, price, qntFlow, statusLabel, editButton, deleteButton);

            {
                layout.setStyle("-fx-padding: 10; -fx-border-color: #000; -fx-border-width: 0 0 1 0;" );

                descriptionScroll.setContent(descFlow);
                descriptionScroll.setPrefHeight(100);
                descriptionScroll.setPrefWidth(200);
                descriptionScroll.setFitToWidth(true);
                descriptionScroll.setFitToHeight(false);
                descriptionScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                descriptionScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                description.setWrappingWidth(200);
                descFlow.setPrefWidth(200);
                descFlow.setMaxWidth(200);

                boldName.setStyle("-fx-font-weight: bold;");
                boldDesc.setStyle("-fx-font-weight: bold;");
                boldQnt.setStyle("-fx-font-weight: bold;");

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
                    if (listing.getActive())
                        statusLabel.setText("Active Listing");
                    else
                    {
                        statusLabel.setText("Item Sold");
                        editButton.setVisible(false);
                        deleteButton.setVisible(false);
                    }
                    nameLabel.setText(listing.getName());
                    description.setText(listing.getDescription());
                    price.setText("$"+listing.getPrice());
                    quantity.setText(String.valueOf(listing.getQuantity()));

                    setGraphic(layout);
                }
            }
        });

        pullOwnListings();
    }

}
