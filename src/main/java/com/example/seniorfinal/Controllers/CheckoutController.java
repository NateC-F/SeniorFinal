package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;

public class CheckoutController {
    public void goBack()
    {
        SceneManager.switchTo(SceneID.ListingViewScreen);
    }

}
