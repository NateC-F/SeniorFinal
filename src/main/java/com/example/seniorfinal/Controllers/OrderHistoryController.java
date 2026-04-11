package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.InvoiceCreator;
import com.example.seniorfinal.Core.ListingReceipt;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable
{
    @FXML
    TableColumn<ListingReceipt, String> listingName; //name
    @FXML
    TableColumn<ListingReceipt, String> saleDate; // sell date
    @FXML
    TableColumn<ListingReceipt, String> sellerName; // seller
    @FXML
    TableColumn<ListingReceipt, Integer> salePrice; // sell price
    @FXML
    TableColumn<ListingReceipt, Integer> quantity; // sell amount
    @FXML
    TableColumn<ListingReceipt, String> location; // item location
    @FXML
    TableView<ListingReceipt> orderTable;
    @FXML
    private Text errorText;

    ObservableList<ListingReceipt> receipts;
    //=============================================================================================================
    public void goBack()
    {
        SceneManager.switchTo(SceneID.ProfileScreen);
    }
    //=============================================================================================================
    public void createReceipt()
    {
        if (!receipts.isEmpty())
        {
            ArrayList<ListingReceipt> arrayReceipts = new ArrayList<>(receipts);
            String receiptName = UserSession.getSession().getActiveUser().getAccountName() + "-Purchase-History-Receipt";
            InvoiceCreator.makeReceipt(arrayReceipts,receiptName);
        }
        else
        {
            errorText.setVisible(true);
            errorText.setText("You Have Not Made Any Purchases No Receipt Can Be Made");
        }
    }
    //=============================================================================================================
    public void initColumns()
    {
        listingName.setCellValueFactory(new PropertyValueFactory<>("listingName"));
        saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        sellerName.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
        salePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        salePrice.setCellFactory(col -> new javafx.scene.control.TableCell<>()
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
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        location.setCellFactory(col -> {
            TableCell<ListingReceipt, String> cell = new TableCell<>()
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
                    ListingReceipt order = cell.getTableView().getItems().get(cell.getIndex());
                    double[] addr = {order.getLatitude(),order.getLongitude()};
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
    public void loadTable()
    {
        receipts = FXCollections.observableArrayList(UserSession.getSession().getOrderHistory());
        if (receipts.isEmpty())
        {
            errorText.setText("No Purchases Have Been Made Come Back Later");
            errorText.setVisible(true);
        }
        else
            orderTable.setItems(receipts);
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initColumns();
        loadTable();
    }
}
