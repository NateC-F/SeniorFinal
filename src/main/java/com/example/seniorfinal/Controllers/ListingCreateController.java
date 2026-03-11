package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Category;
import com.example.seniorfinal.Core.Location;
import com.example.seniorfinal.Model.DAO.CategoryDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ListingCreateController implements Initializable
{
    @FXML
    ComboBox<Category> categoryBox;
    @FXML
    ComboBox<String> stateBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        categoryBox.getItems().addAll(new CategoryDAO().pullCategories());
        stateBox.getItems().addAll(new Location().setStates());
    }
}
