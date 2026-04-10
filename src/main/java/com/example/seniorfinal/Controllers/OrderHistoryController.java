package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.Order;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable
{
    @FXML
    TableColumn<Order, String> name; //name
    @FXML
    TableColumn<Order, String> date; // sell date
    @FXML
    TableColumn<Order, String> seller; // seller
    @FXML
    TableColumn<Order, Integer> price; // sell price
    @FXML
    TableColumn<Order, Integer> amount; // sell amount
    @FXML
    TableColumn<Order, String> location; // item location
    @FXML
    TableView<Order> orderTable;
    //=============================================================================================================
    public void goBack()
    {
        SceneManager.switchTo(SceneID.ProfileScreen);
    }

    //=============================================================================================================
    public void initColumns()
    {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        seller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(col -> new javafx.scene.control.TableCell<>()
        {
            @Override
            protected void updateItem(Integer value, boolean empty)
            {
                super.updateItem(value, empty);

                if (empty || value == null)
                {
                    setText(null);
                }
                else
                {
                    setText("$" + value);
                }
            }
        });
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        location.setCellFactory(col -> {
            TableCell<Order, String> cell = new TableCell<>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getClickCount() == 2)
                {
                    Order order = cell.getTableView().getItems().get(cell.getIndex());
                    double[] addr = {order.getListingReceipt().getLatitude(),order.getListingReceipt().getLongitude()};
                    try
                    {
                        showLocationPopup(addr);
                    } catch (IOException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
            });

            return cell;
        });
    }
    //=============================================================================================================
    public void showLocationPopup(double[] address) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/seniorfinal/View/fxml/locationPopUp.fxml"));

        Parent root = loader.load();

        LocationPopUpController controller = loader.getController();
        controller.setCoordinates(address);

        Stage stage = new Stage();
        stage.setTitle("Location");
        stage.setScene(new Scene(root));

        stage.initModality(Modality.APPLICATION_MODAL); // blocks background
        stage.showAndWait();
    }
    //=============================================================================================================
    public void loadTable()
    {
        ObservableList<Order> data = FXCollections.observableArrayList(UserSession.getSession().getOrderHistory());

        orderTable.setItems(data);
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initColumns();
        loadTable();
    }
}
