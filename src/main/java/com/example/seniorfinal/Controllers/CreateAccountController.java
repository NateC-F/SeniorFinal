package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Model.DAO.AccountDAO;
import com.example.seniorfinal.Utilities.DuplicateAccountException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable
{
    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;
    @FXML
    private TextField passField2;
    @FXML
    private Text accountCreateFlag;
    @FXML
    private Text succsessText;
    @FXML
    private ProgressBar passwordStrengthBar;
    @FXML
    private Text strengthText;

    private int score = 0;
    private boolean length = false;
    private boolean number = false;
    private boolean specialChar = false;

    private AccountDAO accountDAO = new AccountDAO();

    //=============================================================================================================
    @FXML
    private void createAccount()
    {
        String username = nameField.getText().trim();
        String password = passField.getText().trim();
        String matchingPass = passField2.getText().trim();
        succsessText.setVisible(false);

        if (!password.equals(matchingPass))
        {
            accountCreateFlag.setText("Passwords do not match");
            return;
        }
        if (score != 3)
        {
            accountCreateFlag.setText("Password is not strong enough");
            return;
        }
        try
        {
            accountDAO.createAccount(username, password);
            accountCreateFlag.setText("");
            succsessText.setVisible(true);
            passField.clear();
            passField2.clear();
            nameField.clear();
            length = false;
            number = false;
            specialChar = false;
            score = 0;
        }
        catch (DuplicateAccountException e)
        {
            accountCreateFlag.setText(e.getMessage());
        }
        catch (Exception e)
        {
            accountCreateFlag.setText("Error creating account, try again later");
        }
    }
    //=============================================================================================================
    private void updatePasswordStrength(String password)
    {
        int tempScore=0;
        length = password.length() >= 8;
        number = password.matches(".*\\d.*");
        specialChar = password.matches(".*[!@#$%^&*()].*");

        if (length)
            tempScore++;

        if (number)
            tempScore++;

        if (specialChar)
            tempScore++;


        double progress = tempScore / 3.0;
        passwordStrengthBar.setProgress(progress);

        if (tempScore == 1)
        {
            passwordStrengthBar.setStyle("-fx-accent: red;");
            strengthText.setText("Weak password");
        }
        else if (tempScore == 2)
        {
            passwordStrengthBar.setStyle("-fx-accent: orange;");
            strengthText.setText("Medium password");
        }
        else if (tempScore == 3)
        {
            passwordStrengthBar.setStyle("-fx-accent: green;");
            strengthText.setText("Strong password");
            score = tempScore;
        }
        else
        {
            passwordStrengthBar.setStyle("-fx-accent: red;");
            strengthText.setText("Too weak");
        }
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        passField.textProperty().addListener((obs, oldVal, newVal) -> {
            updatePasswordStrength(newVal);
        });
        strengthText.setText("Too weak");
    }
}
