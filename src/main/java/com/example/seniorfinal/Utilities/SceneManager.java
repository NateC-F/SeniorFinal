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
    private static int loginResX = 300;
    private static int loginResY = 200;

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
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/login.fxml"), loginResX, loginResY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("Login");
                }
                case CreateAccountScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/createAccount.fxml"), resX/2, resY/2);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("Account Creation");
                }
                case MainScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/main.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace Home");
                }
                case ListingViewScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/listingView.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace Listing");
                }  case ListingCreateScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/listingCreate.fxml"), resX, resY);
                scene.getStylesheets().add(SceneManager.class
                        .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace Listing");
                }
                case ProfileScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/profile.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace User");
                }
                case OrderHistoryScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/orderHistory.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace OrderHistory");
                }
                case CheckoutScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/checkout.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("MarketPlace Checkout");
                }
                case ListingEditScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/editListing.fxml"), resX/2, resY/2);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/mainTheme.css").toExternalForm());
                    stage.setTitle("Edit Your Listing");
                }
                case AdminScreen -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/adminView.fxml"), resX, resY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/adminTheme.css").toExternalForm());
                    stage.setTitle("Admin View");
                }
                case AdminLogin -> {
                    scene = new Scene(loadFXML("/com/example/seniorfinal/View/fxml/adminLogin.fxml"), loginResX, loginResY);
                    scene.getStylesheets().add(SceneManager.class
                            .getResource("/com/example/seniorfinal/View/css/adminTheme.css").toExternalForm());
                    stage.setTitle("Admin Login Screen");
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