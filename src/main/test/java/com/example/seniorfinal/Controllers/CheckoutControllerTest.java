package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutControllerTest
{
    private CheckoutController controller;


    @BeforeAll
    public static void initJFX() {
        // Starts the JavaFX toolkit once
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        controller = new CheckoutController();
        controller.totalPrice = new javafx.scene.text.Text();
        controller.quantityBox = new javafx.scene.control.ComboBox<>();
    }

    @Test
    public void testUpdateTotal_singleQuantity() {
        // Setup a listing with price $10 and quantity 1
        Listing listing = new Listing();
        listing.setPrice(10);
        listing.setQuantity(1);
        UserSession.getSession().setActiveViewListing(listing);

        // Simulate combo box selection
        controller.quantityBox.getItems().add(1);
        controller.quantityBox.setValue(1);

        // Call method under test
        controller.updateTotal();

        // Verify total price
        assertEquals("Total Cost: $10", controller.totalPrice.getText());
    }

    @Test
    public void testUpdateTotal_multipleQuantity() {
        Listing listing = new Listing();
        listing.setPrice(15);
        listing.setQuantity(5);
        UserSession.getSession().setActiveViewListing(listing);

        // Add quantity options to combo box
        for (int i = 1; i <= listing.getQuantity(); i++) {
            controller.quantityBox.getItems().add(i);
        }

        // Test selecting quantity 3
        controller.quantityBox.setValue(3);
        controller.updateTotal();

        assertEquals("Total Cost: $45", controller.totalPrice.getText());

        // Test selecting quantity 5
        controller.quantityBox.setValue(5);
        controller.updateTotal();

        assertEquals("Total Cost: $75", controller.totalPrice.getText());
    }
}
