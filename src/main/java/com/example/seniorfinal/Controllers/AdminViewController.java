package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.*;
import com.example.seniorfinal.Model.DAO.AccountDAO;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import javafx.beans.binding.ObjectExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable
{
    private ArrayList<UserAccount> accounts = new ArrayList<>();
    private ArrayList<Listing> listings = new ArrayList<>();


    @FXML
    private ListView<ListViewItem> listView = new ListView<>();
    @FXML
    private MenuItem menuAccounts;
    @FXML
    private MenuItem menuListings;


    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        pullListings();
        pullAccounts();

        menuAccounts.setOnAction(e -> {
            listView.getItems().setAll(accounts);
        });
        menuListings.setOnAction(e -> {
            listView.getItems().setAll(listings);
        });
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ListViewItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                HBox root = new HBox(10);
                root.setAlignment(Pos.CENTER_LEFT);

                VBox textContainer = new VBox(5);
                Button deleteBtn = new Button("Delete");

                if (item instanceof UserAccount account) {

                    Label name = new Label(account.getAccountName());
                    Label date = new Label("Created: " + account.getCreationDate());

                    textContainer.getChildren().addAll(name, date);
                }
                else if (item instanceof Listing listing) {

                    ImageView imageView = new ImageView(ImageBlob.blobToImage(listing.getListing_image()));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    Label name = new Label(listing.getName());
                    Label desc = new Label(listing.getDescription());

                    textContainer.getChildren().addAll(name, desc);

                    root.getChildren().add(imageView);
                }

                deleteBtn.setOnAction(e -> {
                    if ((item instanceof Listing listing))
                    {
                        new ListingDAO().deleteListing(listing.getId());
                    }
                    else if (item instanceof UserAccount account)
                    {
                        new AccountDAO().deleteAccount(account.getAccountID());
                    }
                    getListView().getItems().remove(item);
                });

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                root.getChildren().addAll(textContainer, spacer, deleteBtn);

                setGraphic(root);
            }
        });
    }
    //=============================================================================================================
    public void pullAccounts()
    {
        accounts = new AccountDAO().getUserAccounts();

    }
    //=============================================================================================================
    public void pullListings()
    {
       listings = new ListingDAO().getAllActiveListings();
    }
    //=============================================================================================================
    public void close()
    {
        javafx.application.Platform.exit();
    }

}
