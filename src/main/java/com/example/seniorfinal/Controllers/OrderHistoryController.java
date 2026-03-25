package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;

public class OrderHistoryController
{
    public void goBack()
    {
        SceneManager.switchTo(SceneID.ProfileScreen);
    }

}
