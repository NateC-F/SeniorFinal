package com.example.seniorfinal;

import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import com.example.seniorfinal.Utilities.StripeConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        StripeConfig.init();
        SceneManager.setStage(stage);
        SceneManager.switchTo(SceneID.LoginScreen);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}