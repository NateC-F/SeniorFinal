package com.example.seniorfinal;

import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.PasswordHash;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminApp extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        SceneManager.setStage(stage);
        SceneManager.switchTo(SceneID.AdminLogin);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}