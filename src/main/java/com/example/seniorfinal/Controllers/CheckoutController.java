package com.example.seniorfinal.Controllers;

import com.example.seniorfinal.Core.CartItem;
import com.example.seniorfinal.Core.Listing;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Model.DAO.ListingDAO;
import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.SceneID;
import com.example.seniorfinal.Utilities.SceneManager;
import com.example.seniorfinal.Utilities.StripePayment;
import com.stripe.model.PaymentIntent;
import com.stripe.model.tax.Registration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable
{

    @FXML
    Text totalPrice;
    @FXML
    ComboBox<Integer> quantityBox;
    @FXML
    private ListView<CartItem> cartListView;

    String sqlCode;
    //=============================================================================================================
    public void goBack()
    {
        SceneManager.switchTo(SceneID.MainScreen);
    }
    //=============================================================================================================
    @FXML
    public void updateTotal()
    {
        totalPrice.setText("Total Cost: $"+ (UserSession.getSession().getUserCart().getCartTotal()));
    }
    //=============================================================================================================

    //=============================================================================================================
    @FXML
    public void purchase()
    {
        long chargeAmount = UserSession.getSession().getUserCart().getCartTotal() * 100L; //Stripe api requires the amount to be in cents

        try{
            for (CartItem items : UserSession.getSession().getUserCart().getItems())
            {
                new ListingDAO().purchase(items);
            }
            UserSession.getSession().getUserCart().removeAllItemsFromCart();

           PaymentIntent paymentIntent = StripePayment.createPaymentIntent(chargeAmount,"usd");
           String clientSecrete = paymentIntent.getClientSecret();
           System.out.println("Client Secrete: "+ clientSecrete+", the total charge was: "+chargeAmount/100);
           System.out.println("Purchase was successful no real payment necessary");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Stripe crashed");
        }
        SceneManager.switchTo(SceneID.MainScreen);
    }
    //=============================================================================================================
    private void loadCart() {
        cartListView.getItems().setAll(UserSession.getSession().getUserCart().getItems());
        updateTotal();
    }
    //=============================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadCart();
        cartListView.setCellFactory(lv -> new ListCell<>() {
            private Text priceText; // field, not local variable

            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Column 1: Name (fixed width)
                    Label nameLabel = new Label(item.getListing().getName());
                    nameLabel.setPrefWidth(200);
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    // Column 2: Quantity Spinner
                    Spinner<Integer> quantitySpinner = new Spinner<>(1, item.getMaxQuantity(), item.getQuantity());
                    quantitySpinner.setPrefWidth(80);

                    // Column 3: Price (Text node as a field)
                    priceText = new Text("$" + item.getTotal());
                    priceText.setWrappingWidth(100);

                    // Update price when spinner changes
                    quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                        item.setQuantity(newVal);
                        UserSession.getSession().getUserCart().updateQuantity(item.getListing(), newVal);
                        priceText.setText("$" + item.getTotal());
                        updateTotal();
                    });

                    // Column 4: Remove Button
                    Button removeButton = new Button("Remove");
                    removeButton.setPrefWidth(100);
                    removeButton.setOnAction(e -> {
                        UserSession.getSession().getUserCart().removeFromCart(item);
                        cartListView.getItems().remove(item);
                        updateTotal();
                    });

                    // Spacer to push remove button to the right
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    // Combine into HBox (columns aligned)
                    HBox hBox = new HBox(10, nameLabel, quantitySpinner, priceText, spacer, removeButton);
                    hBox.setStyle("-fx-padding: 5; -fx-alignment: CENTER_LEFT;");

                    setGraphic(hBox);
                }
            }
        });
    }
}
