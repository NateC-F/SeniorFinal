package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.InvoiceCreator;
import com.example.seniorfinal.Core.ListingReceipt;
import com.example.seniorfinal.Core.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SaleHistoryController implements Initializable
{
    @FXML
    TableColumn<ListingReceipt, String> listingName; //name
    @FXML
    TableColumn<ListingReceipt, String> saleDate; // sell date
    @FXML
    TableColumn<ListingReceipt, String> buyerName; // buyer
    @FXML
    TableColumn<ListingReceipt, Integer> salePrice; // sell price
    @FXML
    TableColumn<ListingReceipt, Integer> quantity; // sell amount
    @FXML
    TableView<ListingReceipt> orderTable;
    @FXML
    private Text errorText;
    ObservableList<ListingReceipt> receipts;


    //=============================================================================================================
    public void createReceipt()
    {
        if (receipts.isEmpty())
        {
            errorText.setVisible(true);
            errorText.setText("No Sales Have Been Made So A Receipt Cannot Be Made");
        }
        else
        {
            ArrayList<ListingReceipt> arrayReceipts = new ArrayList<>(receipts);
            String receiptName = UserSession.getSession().getActiveUser().getAccountName() + "-Sale-History-Receipt";
            InvoiceCreator.makeReceipt(arrayReceipts,receiptName);
        }
    }
    //=============================================================================================================
    public void initColumns()
    {
        listingName.setCellValueFactory(new PropertyValueFactory<>("listingName"));
        saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        buyerName.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
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
                } else
                {
                    setText("$" + value);
                }
            }
        });
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
    //=============================================================================================================
    public void loadTable()
    {
        receipts= FXCollections.observableArrayList(UserSession.getSession().getSaleHistory());
        if (receipts.isEmpty())
        {
            errorText.setText("No Sales Have Been Made Come Back Later");
            errorText.setVisible(true);
        }
        else
            orderTable.setItems(receipts);
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        initColumns();
        loadTable();
    }
}
