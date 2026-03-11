package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Model.DAO.AccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CreateAccountController
{
    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;
    @FXML
    private Text accountCreateFlag;

    private AccountDAO accountDAO = new AccountDAO();

    //=============================================================================================================

    @FXML
    private void createAccount()
    {
        String username = nameField.getText();
        String password = passField.getText();
        if (accountDAO.createAccount(username,password))
        {
            accountCreateFlag.setFill(Color.GREEN);
            accountCreateFlag.setText("ACCOUNT CREATED YOU MAY CLOSE THIS TAB");
        }
        else {
            accountCreateFlag.setFill(Color.RED);
            accountCreateFlag.setText("ACCOUNT COULD NOT BE CREATED");
        }

        accountCreateFlag.setVisible(true);
    }

}
