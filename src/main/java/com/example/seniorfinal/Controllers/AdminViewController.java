package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Account;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserAccount;
import com.example.seniorfinal.Model.DAO.AccountDAO;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable
{
    ArrayList<Account> accounts;
    ArrayList<Listing> listings;

//    @FXML
//    private TableColumn<> C1;
//    @FXML
//    private TableColumn<> C2;
//    @FXML
//    private TableColumn<> C3;
//    @FXML
//    private TableColumn<> C4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        pullListings();
        pullAccounts();
    }

    public void pullAccounts()
    {
        accounts = new AccountDAO().getUserAccounts();
    }
    public void pullListings()
    {
        listings = new ListingDAO().getAllActiveListings();
    }

}
