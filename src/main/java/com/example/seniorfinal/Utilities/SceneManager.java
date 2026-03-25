package com.example.seniorfinal.Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager
{
    private static Stage stage;
    private static int resX = 1280;
    private static int resY = 720;

    public static void setStage(Stage s)
    {
        stage = s;
    }

    public static void switchTo(SceneID id)
    {
        Scene scene = null;

        try
        {
            switch (id)
            {
                case LoginScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/login.fxml"), resX, resY);
                    stage.setTitle("Login");
                }
                case CreateAccountScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/createAccount.fxml"), resX/2, resY/2);
                    stage.setTitle("Account Creation");
                }
                case MainScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/main.fxml"), resX, resY);
                    stage.setTitle("MarketPlace Home");
                }
                case ListingViewScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/listingView.fxml"), resX, resY);
                    stage.setTitle("MarketPlace Listing");
                }  case ListingCreateScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/listingCreate.fxml"), resX, resY);
                    stage.setTitle("MarketPlace Listing");
                }
                case ProfileScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/profile.fxml"), resX, resY);
                    stage.setTitle("MarketPlace User");
                }
                case OrderHistoryScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/orderHistory.fxml"), resX, resY);
                    stage.setTitle("MarketPlace OrderHistory");
                }
                case CheckoutScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/checkout.fxml"), resX, resY);
                    stage.setTitle("MarketPlace Checkout");
                }
                case ListingEditScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/editListing.fxml"), resX/2, resY/2);
                    stage.setTitle("Edit Your Listing");
                }
            }

            if(scene != null)
            {
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String path) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
        return loader.load();
    }
}