package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Model.DAO.AccountDAO;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AdminLoginController
{
    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;
    @FXML
    private Text errorFlag;


    private AccountDAO accountDAO = new AccountDAO();


    @FXML
    private void login()
    {
        errorFlag.setFill(Color.RED);
        String username = nameField.getText();
        String password = passField.getText();

        String loginFlag = accountDAO.login(username, password, true);

        switch (loginFlag)
        {
            case "SUCCESS":
                SceneManager.switchTo(SceneID.MainScreen);
                break;
            case "USERNAME ERROR":
                errorFlag.setVisible(true);
                errorFlag.setText("USERNAME NOT FOUND TRY AGAIN");
                break;
            case "PASSWORD ERROR":
                errorFlag.setVisible(true);
                errorFlag.setText("PASSWORD WRONG TRY AGAIN");
                break;
            case "OTHER ERROR":
                errorFlag.setVisible(true);
                errorFlag.setText("OTHER ERROR TRY AGAIN");
                break;
            case "NULL NAME":
                errorFlag.setVisible(true);
                errorFlag.setText("NAME FIELD CANNOT BE BLANK");
                break;
            case "NULL PASS":
                errorFlag.setVisible(true);
                errorFlag.setText("PASSWORD FIELD CANNOT BE BLANK");
                break;
        }
    }
}
